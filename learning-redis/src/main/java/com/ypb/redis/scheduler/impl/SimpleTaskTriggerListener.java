package com.ypb.redis.scheduler.impl;

import com.ypb.redis.scheduler.TaskTriggerListener;
import java.math.BigDecimal;
import lombok.Setter;
import org.springframework.data.redis.core.RedisTemplate;

public class SimpleTaskTriggerListener implements TaskTriggerListener {

	@Setter
	private RedisTemplate redisTemplate;

	@Override
	public void taskTriggered(String taskID) {
		System.out.println("taskID = " + taskID);
		String key = "redis:schedule:taskID";

		redisTemplate.boundValueOps(key).increment(BigDecimal.ONE.longValue());
	}
}
