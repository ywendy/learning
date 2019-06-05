package com.ypb.timer.v2;

import java.util.Objects;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName: TimingWheel
 * @Description: 时间轮，可以推进时间和添加任务(https://www.jianshu.com/p/87240220097b http://bigdata.51cto.com/art/201806/575502.htm)
 * @author yangpengbing
 * @date 2019-05-30-15:36
 * @version V1.0.0
 *
 */
public class TimingWheel {

	/**
	 * 时间格代表时间(单位毫秒)
	 */
	private Long tickMs;
	/**
	 * 时间轮格子的数量
	 */
	private Integer wheelSize;
	/**
	 * 时间跨度
	 */
	private Long interVal;
	/**
	 * 表示该时间轮的任务总数
	 */
	private AtomicInteger taskCounter;
	/**
	 * 延迟队列，每个槽中都有一个对应的TimerTaskList，TimerTaskList是一个双向链表，有一个expireTime的值
	 * 这些TimerTaskList都会被加入到这个延迟队列中，expireTime最小的槽会排在队列的最前面
	 */
	private DelayQueue<TimerTaskList> queue;
	/**
	 * 表示的是当前的时间，也就是时间轮指针指向的时间
	 */
	private Long currentTime;
	private volatile TimingWheel overflowWheel;
	/**
	 * 表示TimerTaskList的数组，即各个槽
	 */
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
