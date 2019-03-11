package com.ypb.timer;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yangpengbing
 * @version V1.0.0
 * @ClassName: TimedTask
 * @Description: 需要延迟的任务
 * @date 2019/1/24-12:15
 */
@Slf4j
@EqualsAndHashCode
public class TimedTask {

	/**
	 * 延迟多久执行时间
	 */
	@Getter
	private long delayMs;

	/**
	 * 过期时间戳
	 */
	@Getter
	private long expireTimestamp;

	/**
	 * 任务
	 */
	@Getter
	private Runnable task;

	/**
	 * 是否取消
	 */
	@Getter
	@Setter
	private volatile boolean cancle;

	protected Bucket bucket;

	protected TimedTask next;
	protected TimedTask pre;

	public TimedTask(long delayMs, Runnable task) {
		this.delayMs = delayMs;
		this.task = task;
		this.cancle = false;
		this.expireTimestamp = System.currentTimeMillis() + delayMs;
	}
}
