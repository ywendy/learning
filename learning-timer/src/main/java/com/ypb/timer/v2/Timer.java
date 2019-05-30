package com.ypb.timer.v2;

public interface Timer {

	/**
	 * add a new task to this executor, it will be executed after the task's delay
	 * (beginning from the time of submission)
	 * @param timerTask
	 */
	void add(TimerTask timerTask);

	/**
	 * get the number of tasks pending execution
	 * @return the number of tasks
	 */
	int size();

	/**
	 * shutdown the timer service, leaving pending tasks unexecuted
	 */
	void shutdown();
}
