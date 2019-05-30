package com.ypb.timer.v2;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yangpengbing
 * @version V1.0.0
 * @ClassName: TimerTaskEntity
 * @Description: 封装的定时任务
 * @date 2019-05-30-17:38
 */
public class TimerTaskEntity implements Comparable<TimerTaskEntity> {

	@Getter
	@Setter
	private volatile TimerTaskList list;
	@Getter
	private Long expirationMs;
	@Getter
	private TimerTask timerTask;

	public TimerTaskEntity next;
	public TimerTaskEntity prev;

	public TimerTaskEntity(TimerTask timerTask, Long expirationMs) {
		if (Objects.nonNull(timerTask)) {
			timerTask.setTimerTaskEntity(this);
		}
		this.timerTask = timerTask;
		this.expirationMs = expirationMs;
	}

	public boolean cancelled() {
		return timerTask.getTimerTaskEntity() != this;
	}

	/**
	 * if remove is called when another thread is moving the entry from task entry list to another.
	 * this may fail to remove the entry due to the change of value of list. thus we retry until the list becomes null.
	 * in a rare case. this thread sees null and exits the loop. but the other thread insert the entry to another list later.
	 */
	public void remove() {
		TimerTaskList currentList = this.list;
		while (Objects.nonNull(currentList)) {
			currentList.remove(this);
			currentList = list;
		}
	}

	@Override
	public int compareTo(TimerTaskEntity that) {
		if (Objects.isNull(that)) {
			throw new NullPointerException("timerTaskEntity is null.");
		}

		Long expirationMs1 = this.expirationMs;
		Long expirationMs2 = that.expirationMs;

		if (expirationMs1 < expirationMs2) {
			return -1;
		} else if (expirationMs1 > expirationMs2) {
			return 1;
		}

		return 0;
	}
}
