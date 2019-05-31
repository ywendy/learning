package com.ypb.timer.v2;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

public class TimerTask implements Runnable {

	@Getter
	@Setter
	private int delayMs;
	@Getter
	private TimerTaskEntity timerTaskEntity;

	public TimerTask() {
		this(3000);
	}

	public TimerTask(int delayMs) {
		if (delayMs < 0L) {
			throw new IllegalArgumentException("delayMs must greater than zero");
		}
		this.delayMs = delayMs;
	}

	/**
	 * if this timerTask is already held by an existing timer task entry.
	 * we will remove such an entry first.
	 * @param entity
	 */
	protected synchronized void setTimerTaskEntity(TimerTaskEntity entity) {
		if (Objects.nonNull(timerTaskEntity) && timerTaskEntity != entity) {
			timerTaskEntity.remove();
		}

		timerTaskEntity = entity;
	}

	@Override
	public void run() {
		System.out.println("do the job");
	}
}
