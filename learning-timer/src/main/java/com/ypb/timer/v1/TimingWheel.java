package com.ypb.timer.v1;

import java.util.concurrent.DelayQueue;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yangpengbing
 * @version V1.0.0
 * @ClassName: TimingWheel
 * @Description: 时间轮，可以推进时间和添加任务
 * @date 2019/1/24-11:48
 */
@Slf4j
public class TimingWheel {

	/**
	 * 一个时间槽的时间
	 */
	private long tickMs;

	/**
	 * 时间轮的大小
	 */
	private int wheelSize;

	/**
	 * 时间跨度
	 */
	private long interval;

	/**
	 * 槽
	 */
	private Bucket[] buckets;

	/**
	 * 时间轮指针
	 */
	private long currentTimestamp;

	/**
	 * 上层时间轮
	 */
	private volatile TimingWheel overflowWheel;

	/**
	 * 对应一个Timer以及附属的时间轮，都只有一个delayQueue
	 */
	private DelayQueue<Bucket> delayQueue;

	/**
	 * 创建时间轮对象
	 */
	public TimingWheel(long tickMs, int wheelSize, long currentTimestamp, DelayQueue<Bucket> delayQueue) {
		this.tickMs = tickMs;
		this.wheelSize = wheelSize;
		this.interval = tickMs * wheelSize;
		this.buckets = new Bucket[wheelSize];
		this.currentTimestamp = currentTimestamp - (currentTimestamp % tickMs);
		this.delayQueue = delayQueue;

		for (int i = 0; i < wheelSize; i++) {
			buckets[i] = new Bucket();
		}
	}

	/**
	 * 添加任务到时间轮
	 */
	public boolean addTask(TimedTask task) {
		long expireTimestamp = task.getExpireTimestamp();

		if (task.isCancle()) {
			log.info("task Cancelled");
			return Boolean.FALSE;
		} else if (expireTimestamp <= Math.addExact(currentTimestamp, tickMs)) {
			log.info("当前任务已过期");
			return Boolean.FALSE;
		} else if (expireTimestamp < Math.addExact(currentTimestamp, interval)) {
			// 查找时间格的位置，过期时间/时间格%时间轮大小
			long virtualId = expireTimestamp / tickMs;

			Bucket bucket = buckets[(int) (virtualId % wheelSize)];
			bucket.addTask(task);
			task.bucket = bucket;

			if (bucket.setExpire(expireTimestamp)) {
				log.info("bucket {}", bucket);

				delayQueue.offer(bucket);
			}

			return Boolean.TRUE;
		} else {
			TimingWheel wheel = getOverflowWheel();

			return wheel.addTask(task);
		}
	}

	private TimingWheel getOverflowWheel() {
		if (overflowWheel == null) {
			synchronized (this) {
				if (overflowWheel == null) {
					overflowWheel = new TimingWheel(interval, wheelSize, currentTimestamp, delayQueue);
				}
			}
		}

		return overflowWheel;
	}

	/**
	 * 推进下指针
	 */
	public void advanceClock(long timestamp) {

		if (timestamp >= Math.addExact(currentTimestamp, tickMs)) {
			currentTimestamp = timestamp;

			if (overflowWheel != null) {
				this.overflowWheel.advanceClock(timestamp);
			}
		}
	}
}
