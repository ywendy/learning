package com.ypb.timer.v2;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

public class TimerTask implements Runnable {

	@Getter
	@Setter
	private Long delayMs;
	@Getter
	private TimerTaskEntity timerTaskEntity;

	public TimerTask() {
		this(3000L);
	}

	public TimerTask(long delayMs) {
		this.delayMs = delayMs;
	}

	/**
	 * if this timerTask is already held by an existing timer task entry.
	 * we will remove such an entry first.
	 * @param entity
	 */
	public synchronized void setTimerTaskEntity(TimerTaskEntity entity) {
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
