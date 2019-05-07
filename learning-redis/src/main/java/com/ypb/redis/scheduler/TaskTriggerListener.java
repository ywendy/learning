package com.ypb.redis.scheduler;

/**
 * @author yangpengbing
 * @version V1.0.0
 * @ClassName: TaskTriggerListener
 * @Description: 任务到期的时候回调的接口
 * @date 2019-05-07-16:25
 */
public interface TaskTriggerListener {

	void taskTriggered(String taskID);
}
