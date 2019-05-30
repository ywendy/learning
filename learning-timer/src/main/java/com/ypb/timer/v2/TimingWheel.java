package com.ypb.timer.v2;

import java.util.Objects;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName: TimingWheel
 * @Description: 时间轮，可以推进时间和添加任务
 * @author yangpengbing
 * @date 2019-05-30-15:36
 * @version V1.0.0
 *
 */
public class TimingWheel {

	private Long tickMs;
	private Integer wheelSize;
	private Long interVal;
	private AtomicInteger taskCounter;
	private DelayQueue<TimerTaskList> queue;
	private Long currentTime;
	private volatile TimingWheel overflowWheel;
	private TimerTaskList[] buckets;

	public TimingWheel(Long tickMs, Integer wheelSize, Long startMs, AtomicInteger taskCounter,
			DelayQueue<TimerTaskList> queue) {

		this.tickMs = tickMs;
		this.wheelSize = wheelSize;
		this.taskCounter = taskCounter;
		this.queue = queue;
		this.interVal = tickMs * wheelSize;
		this.currentTime = startMs - (startMs % tickMs);
		this.buckets = new TimerTaskList[wheelSize];

		init();
	}

	private void init() {
		for (int i = 0; i < buckets.length; i++) {
			buckets[i] = new TimerTaskList(taskCounter);
		}
	}

	public boolean add(TimerTaskEntity entity) {
		Long expirationMs = entity.getExpirationMs();

		// cancelled
		if (entity.cancelled()) {
			return Boolean.FALSE;
		} else if (expirationMs < Math.addExact(currentTime, tickMs)) {
			// already expired
			return Boolean.FALSE;
		} else if (expirationMs < Math.addExact(currentTime, interVal)) {
			// put in its own bucket
			long virtualId = expirationMs / tickMs;
			TimerTaskList bucket = buckets[(int) Math.floorMod(virtualId, wheelSize)];
			bucket.add(entity);

			// set the bucket expiration time
			if (bucket.setExpiration(Math.multiplyExact(virtualId, tickMs))) {
				queue.offer(bucket);
			}

			return Boolean.TRUE;
		} else {
			// out of the interval. put it into the parent timer
			if (Objects.isNull(overflowWheel)) {
				addOverflowWheel();
			}

			return overflowWheel.add(entity);
		}
	}

	/**
	 * add overflow timingWheel
	 */
	private synchronized void addOverflowWheel() {
		if (Objects.isNull(overflowWheel)) {
			overflowWheel = new TimingWheel(interVal, wheelSize, currentTime, taskCounter, queue);
		}
	}

	/**
	 * try to advance the clock
	 * @param expirationMs
	 */
	public void advanceClock(Long expirationMs) {
		if (expirationMs < Math.addExact(currentTime, tickMs)) {
			return;
		}

		currentTime = Math.subtractExact(expirationMs, Math.floorMod(expirationMs, tickMs));

		// try to advance the clock of the overflow wheel if present
		if (Objects.nonNull(overflowWheel)) {
			overflowWheel.advanceClock(expirationMs);
		}
	}
}
