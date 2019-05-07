package com.ypb.redis.scheduler;

import java.util.Calendar;

/**
 * @author yangpengbing
 * @version V1.0.0
 * @ClassName: RedisTaskScheduler
 * @Description: 创建一个未来执行的scheduler
 * @date 2019-05-07-15:43
 */
public interface RedisTaskScheduler {

	/**
	 * 设置监听器，如果任务过期，调用监听器
	 */
	void setTaskTriggerListener(TaskTriggerListener listener);

	/**
	 * 添加未来执行的task
	 * @param taskID
	 * @param trigger
	 */
	void schedule(String taskID, Calendar trigger);

	/**
	 * 失效所有的task
	 */
	void unscheduleAllTasks();

	/**
	 * 实现指定的task
	 * @param taskID
	 */
	void unscheduleTask(String taskID);
}
