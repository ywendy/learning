package com.ypb.redis.scheduler.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

/**
 * @ClassName: RedisTaskSchedulerImplTest
 * @Description: 使用Mokito测试
 * @author yangpengbing
 * @date 2019-05-08-10:34
 * @version V1.0.0
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class RedisTaskSchedulerImplTest {

	public static final int MAX_RETRIES = 3;
	@InjectMocks
	private RedisTaskSchedulerImpl scheduler = new RedisTaskSchedulerImpl();
	@Mock
	private RedisTemplate<String, String> redisTemplate;

	@Before
	public void setUp() {
		scheduler.setPollingDelayMillis(50);
		scheduler.setMaxRetriesOnConnectionFailure(MAX_RETRIES);
	}

	@After
	public void shutdown() {
		scheduler.destroy();
	}

	@Test
	public void testCanRetryAfterRedisConnectionError() throws InterruptedException {
		Mockito.doThrow(RedisConnectionFailureException.class).when(redisTemplate)
				.execute(Mockito.any(SessionCallback.class));

		scheduler.initialize();
		Thread.sleep(500);

		Mockito.verify(redisTemplate, Mockito.times(MAX_RETRIES)).execute(Mockito.any(SessionCallback.class));
	}
	
	@Test
	public void testCanRecoverAfterSingleConnectionError() throws InterruptedException {
		Mockito.when(redisTemplate.execute(Mockito.any(SessionCallback.class)))
				.thenThrow(RedisConnectionFailureException.class).thenReturn(true);

		scheduler.initialize();
		Thread.sleep(500);

		Mockito.verify(redisTemplate, Mockito.atLeast(Math.incrementExact(MAX_RETRIES)))
				.execute(Mockito.any(SessionCallback.class));
	}
}