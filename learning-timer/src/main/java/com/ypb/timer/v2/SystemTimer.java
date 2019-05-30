package com.ypb.timer.v2;

import static com.ypb.timer.v2.Time.getHiresClockMs;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SystemTimer implements Timer, Function<TimerTaskEntity, Void> {

	private final long timeoutMs;
	private ExecutorService taskExecutor;
	private final Long tickMs;
	private final Integer wheelSize;
	private final Long startMs;
	private final DelayQueue<TimerTaskList> delayQueue = new DelayQueue<>();
	private final AtomicInteger taskCounter = new AtomicInteger(BigDecimal.ZERO.intValue());
	private final TimingWheel timingWheel;
	private final AdvanceThread thread;

	/**
	 * locks used to protect data structures while ticking
	 */
	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private final ReentrantReadWriteLock.ReadLock readLock = rwl.readLock();
	private final ReentrantReadWriteLock.WriteLock writeLock = rwl.writeLock();

	public SystemTimer(long timeoutMs) {
		this.tickMs = 1L;
		this.wheelSize = 20;
		this.startMs = getHiresClockMs();
		this.taskExecutor = initTaskExecutor(Integer.MAX_VALUE);
		this.timingWheel = new TimingWheel(tickMs, wheelSize, startMs, taskCounter, delayQueue);
		this.timeoutMs = timeoutMs;
		this.thread = new AdvanceThread();
	}

	private ExecutorService initTaskExecutor(int capacity) {
		int core = Runtime.getRuntime().availableProcessors();
		int maxCore = core * 2 + 1;
		ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("timer-pool-%d").build();

		return new ThreadPoolExecutor(core, maxCore, BigDecimal.ZERO.longValue(), TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<>(capacity), factory);
	}

	@Override
	public void add(TimerTask timerTask) {
		readLock.lock();
		try {
			if (!thread.isAlive()) {
				thread.start();
			}
			addTimerTaskEntity(new TimerTaskEntity(timerTask, timerTask.getDelayMs() + getHiresClockMs()));
		}finally {
			readLock.unlock();
		}
	}

	private void addTimerTaskEntity(TimerTaskEntity entity) {
		// already expired or cancelled
		if (!timingWheel.add(entity) && !entity.cancelled()) {
			taskExecutor.submit(entity.getTimerTask());
		}
	}

	/**
	 * advance the internal clock, executing any tasks whose expiration has been reached within the duration of the
	 * passed timeout
	 *
	 * @return whether or not any tasks were executed
	 */
	private Boolean advanceClock(long timeoutMs){
		try {
			TimerTaskList bucket = delayQueue.poll(timeoutMs, TimeUnit.MILLISECONDS);

			if (Objects.isNull(bucket)) {
				return Boolean.FALSE;
			}

			writeLock.lock();
			try {
				while (Objects.nonNull(bucket)) {
					timingWheel.advanceClock(bucket.getExpiration());
					bucket.flush(this);
					bucket = delayQueue.poll();
				}
			}finally {
				writeLock.unlock();
			}

			return Boolean.TRUE;
		} catch (InterruptedException e) {
			log.debug(e.getMessage(), e);
		}

		return Boolean.FALSE;
	}

	@Override
	public int size() {
		return taskCounter.get();
	}

	@Override
	public void shutdown() {
		taskExecutor.shutdown();
		thread.stopThread();
	}

	/**
	 * applies this function to the given argument.
	 * @param entity
	 * @return
	 */
	@Override
	public Void apply(TimerTaskEntity entity) {
		addTimerTaskEntity(entity);
		return null;
	}

	/**
	 * advance clock thread
	 */
	private class AdvanceThread extends Thread {

		private volatile boolean stop = false;

		public AdvanceThread() {
			super("advanceThread");
		}

		@Override
		public void run() {
			while (!stop) {
				advanceClock(timeoutMs);
			}
		}

		public void stopThread() {
			stop = Boolean.TRUE;
		}
	}
}
