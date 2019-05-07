package com.ypb.redis.scheduler.impl;

import com.google.common.collect.Maps;
import com.ypb.redis.scheduler.RedisTaskScheduler;
import com.ypb.redis.scheduler.TaskTriggerListener;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.util.CollectionUtils;

@Slf4j
public class RedisTaskSchedulerImpl implements RedisTaskScheduler {

	public static final String SCHEDULE_KEY = "redis:scheduler:%s";
	public static final String DEFAULT_SCHEDULER_NAME = "scheduler-polling";

	private TaskTriggerListener listener;
	@Setter
	private RedisTemplate redisTemplate;
	private PollingThread pollingThread;
	@Setter
	private int maxRetriesOnConnectionFailure = 1;
	@Setter
	private Clock clock;
	@Setter
	private int pollingDelayMillis = 500;
	private String scheduleName = DEFAULT_SCHEDULER_NAME;

	@PostConstruct
	public void RedisTaskSchedulerImpl() {
		pollingThread = new PollingThread(scheduleName);
		clock = new StandardClock();

		pollingThread.start();

		String template = "[${scheduleName}] Started Redis Scheduler (polling freq: [${pollingDelayMills}ms])";
		Map<String, Object> map = Maps.newHashMapWithExpectedSize(2);
		map.put("scheduleName", scheduleName);
		map.put("pollingDelayMills", pollingDelayMillis);

		printLog(template, map);
	}

	@PreDestroy
	public void destroy() {
		if (Objects.isNull(pollingThread)) {
			return;
		}

		pollingThread.requestStop();
	}

	private void printLog(String template, Map<String, Object> map) {
		if (CollectionUtils.isEmpty(map)) {
			log.info(template);

			return;
		}

		String msg = StringSubstitutor.replace(template, map);
		log.info(msg);
	}

	@Override
	public void setTaskTriggerListener(TaskTriggerListener listener) {
		this.listener = listener;
	}

	@Override
	public void schedule(String taskID, Calendar trigger) {
		if (Objects.isNull(trigger)) {
			trigger = clock.now();
		}

		getzSetOperations().add(keyForScheduler(), taskID, trigger.getTimeInMillis());
	}

	private ZSetOperations getzSetOperations() {
		return redisTemplate.opsForZSet();
	}

	private String keyForScheduler() {
		return String.format(SCHEDULE_KEY, DEFAULT_SCHEDULER_NAME);
	}

	@Override
	public void unscheduleAllTasks() {
		redisTemplate.delete(keyForScheduler());
	}

	@Override
	public void unscheduleTask(String taskID) {
		getzSetOperations().remove(keyForScheduler(), taskID);
	}

	private class PollingThread extends Thread {

		private boolean stopRequested = false;
		private int numRetriesAttempted = 0;

		public PollingThread(String scheduleName) {
			super(scheduleName);
		}

		public void requestStop() {
			stopRequested = true;
		}

		@Override
		public void run() {
			while (!stopRequested && !isMaxRetriesAttemptedReached()) {
				try {
					attemptTriggerNextTask();
				} catch (Exception e) {
					log.debug(e.getMessage(), e);
					break;
				}
			}

			if (isMaxRetriesAttemptedReached()) {
				String template = "[scheduleName] Error while polling scheduled tasks. no additional scheduled task will be triggered until the application is restarted.";
				Map<String, Object> map = Maps.newHashMapWithExpectedSize(BigDecimal.ONE.intValue());
				map.put("scheduleName", scheduleName);

				log.error(StringSubstitutor.replace(template, map));
			} else {
				log.warn("[{}] redis scheduler stop.", scheduleName);
			}
		}

		private void attemptTriggerNextTask() throws InterruptedException {
			try {
				boolean taskTriggered = triggerNextTaskIfFound();

				if (!taskTriggered) {
					sleep(pollingDelayMillis);
				}

				resetRetriesAttemptsCount();
			} catch (RedisConnectionFailureException e) {
				incrementRetriesAttemptsCount();

				log.warn("connection failure during scheduler polling (attempt ()/())", numRetriesAttempted, maxRetriesOnConnectionFailure);
			}
		}

		private void incrementRetriesAttemptsCount() {
			numRetriesAttempted++;
		}

		private void resetRetriesAttemptsCount() {
			numRetriesAttempted = 0;
		}

		private boolean isMaxRetriesAttemptedReached() {
			return numRetriesAttempted >= maxRetriesOnConnectionFailure;
		}
	}

	private boolean triggerNextTaskIfFound() {

		return (boolean) redisTemplate.execute(new SessionCallback<Boolean>() {

			@Override
			public Boolean execute(RedisOperations operations) throws DataAccessException {
				boolean taskWasTriggered = false;
				final String key = keyForScheduler();

				operations.watch(key);

				String task = findFirstTaskDueForExecution(operations);

				if (StringUtils.isBlank(task)) {
					operations.unwatch();
				} else {
					operations.multi();

					operations.opsForZSet().remove(key, task);
					boolean success = operations.exec() != null;

					Map<String, Object> map = initMap(task);
					String template = "[${scheduleName}] triggering execution of task [${task}]";

					if (success) {
						log.info(StringSubstitutor.replace(template, map));
						tryTaskExecution(task);

						taskWasTriggered = true;
					} else {
						template = "[${scheduleName}] Race condition detected for triggering of task [${task}]. The task has probably been triggered by another instance of this application.";

						log.warn(StringSubstitutor.replace(template, map));
					}
				}

				return taskWasTriggered;
			}

			private Map<String, Object> initMap(String task) {
				Map<String, Object> map = Maps.newHashMapWithExpectedSize(2);
				map.put("scheduleName", scheduleName);
				map.put("task", task);
				return map;
			}
		});
	}

	private void tryTaskExecution(String task) {
		try {
			listener.taskTriggered(task);
		} catch (Exception e) {
			log.debug("[{}] Error during execution of task [{}] exception [{}]", scheduleName, task, e);
		}
	}

	private String findFirstTaskDueForExecution(RedisOperations operations) {
		final long minScore = 0;
		final long maxScore = System.currentTimeMillis();

		Set<byte[]> bytes = (Set<byte[]>) operations.execute((RedisCallback<Set<byte[]>>) connection -> {
			String key = keyForScheduler();
			return connection.zRangeByScore(key.getBytes(), minScore, maxScore, 0, 1);
		});

		if (CollectionUtils.isEmpty(bytes)) {
			return StringUtils.EMPTY;
		}

		Object deserialize = operations.getValueSerializer().deserialize(bytes.iterator().next());

		return Objects.isNull(deserialize) ? StringUtils.EMPTY : deserialize.toString();
	}
}
