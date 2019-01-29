package com.ypb.timer;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * @ClassName: Timer
 * @Description: 定时器
 * @author yangpengbing
 * @date 2019/1/24-11:22
 * @version V1.0.0
 *
 */
@Slf4j
public class Timer {

	/**
	 * 定义最顶层的时间轮
	 */
	private TimingWheel timingWheel;

	/**
	 * 对于一个Timer以及附属的时间轮，都只有一个delayQueue对象
	 */
	private DelayQueue<Bucket> delayQueue = new DelayQueue<>();

	/**
	 * 只有一个线程的无限阻塞队列线程池
	 */
	private ExecutorService pool;

	private ExecutorService boosPool;

	@Getter(lazy = true)
	private static final Timer instance = new Timer();

	private AtomicLong counter = new AtomicLong(0);

	private Timer() {
		pool = Executors.newFixedThreadPool(100,new ThreadFactoryBuilder().setPriority(10).setNameFormat("worker-pool").build());
		boosPool = Executors.newFixedThreadPool(1, new ThreadFactoryBuilder().setPriority(10).setNameFormat("boos-pool").build());

		timingWheel = new TimingWheel(200, 100, System.currentTimeMillis(), delayQueue);
		boosPool.execute(()->{
			try {
				while (true) {
					if (log.isDebugEnabled()) {
						String currDate = DateTime.now().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));

						log.info("current date {}", currDate);
					}
					Timer.getInstance().advanceClock(20);

					TimeUnit.MILLISECONDS.sleep(1000L);
				}
			} catch (Exception e) {
				log.debug(e.getMessage(), e);
			}
		});
	}

	public void addTask(TimedTask task) {
		if (!timingWheel.addTask(task)) {
			pool.submit(task.getTask());
		}
	}

	/**
	 * 推进一下时间轮的指针，并且将delayQueue中的任务取出来重新在扔进去
	 * @param timeout
	 */
	private void advanceClock(int timeout) {
		try {
			Bucket bucket = delayQueue.poll(timeout, TimeUnit.MILLISECONDS);
			if (bucket != null) {
				timingWheel.advanceClock(bucket.getExpire());
				bucket.flush(this::addTask);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage(), e);
		}
	}
}
