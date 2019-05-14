package com.ypb.redis.scheduler.impl;

import com.ypb.redis.scheduler.Clock;
import com.ypb.redis.scheduler.TaskTriggerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisTaskSchedulerConfig {

	@Value("${spring.redis.host}")
	private String host;
	@Value("${spring.redis.port}")
	private int port;

	@Bean
	public JedisPoolConfig config() {
		return new JedisPoolConfig();
	}

	@Bean
	public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig config) {
		JedisConnectionFactory factory = new JedisConnectionFactory(config);

		factory.setHostName(host);
		factory.setPort(port);

		factory.afterPropertiesSet();
		return factory;
	}

	@Bean
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
		return new StringRedisTemplate(factory);
	}

	@Bean
	public StubbedClock clock (){
		return new StubbedClock();
	}

	@Bean
	public TaskTriggerListener initTaskTriggerListener(RedisTemplate redisTemplate) {
		SimpleTaskTriggerListener listener = new SimpleTaskTriggerListener();
		listener.setRedisTemplate(redisTemplate);

		return listener;
	}

	@Bean
	public RedisTaskSchedulerImpl scheduler(RedisTemplate redisTemplate, Clock clock, TaskTriggerListener listener) {
		RedisTaskSchedulerImpl scheduler = new RedisTaskSchedulerImpl();
		scheduler.setRedisTemplate(redisTemplate);
		scheduler.setClock(clock);
		scheduler.setTaskTriggerListener(listener);

		return scheduler;
	}
}
