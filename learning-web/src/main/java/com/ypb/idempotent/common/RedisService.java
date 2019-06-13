package com.ypb.idempotent.common;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands.SetOption;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;
import redis.clients.util.SafeEncoder;

@Slf4j
@Component
public class RedisService {

	@Autowired
	private StringRedisTemplate redisTemplate;
	@Autowired
	private RedisScript redisScript;

	public void set(String key, String value, Integer time) {
		@Cleanup
		RedisConnection conn = redisTemplate.getConnectionFactory().getConnection();
		conn.set(SafeEncoder.encode(key), SafeEncoder.encode(value), Expiration.seconds(time), SetOption.SET_IF_ABSENT);
	}

	public void setEx(String key, String value, Integer seconds) {
		@Cleanup
		RedisConnection conn = redisTemplate.getConnectionFactory().getConnection();
		conn.setEx(SafeEncoder.encode(key), seconds, SafeEncoder.encode(value));
	}

	public Long del(String token) {
		@Cleanup
		RedisConnection conn = redisTemplate.getConnectionFactory().getConnection();
		return conn.del(SafeEncoder.encode(token));
	}

	public Boolean exists(String key) {
		@Cleanup
		RedisConnection conn = redisTemplate.getConnectionFactory().getConnection();
		return conn.exists(SafeEncoder.encode(key));
	}

	public String get(String key) {
		@Cleanup
		RedisConnection conn = redisTemplate.getConnectionFactory().getConnection();
		byte[] bytes = conn.get(SafeEncoder.encode(key));

		if (ArrayUtils.isEmpty(bytes)) {
			return "0";
		}

		return SafeEncoder.encode(bytes);
	}

	public Long ttl(String key) {
		@Cleanup
		RedisConnection conn = redisTemplate.getConnectionFactory().getConnection();
		return conn.ttl(SafeEncoder.encode(key));
	}

	public void incr(String key){
		@Cleanup
		RedisConnection conn = redisTemplate.getConnectionFactory().getConnection();
		conn.incr(SafeEncoder.encode(key));
	}

	public void expire(String key, int seconds) {
		@Cleanup
		RedisConnection conn = redisTemplate.getConnectionFactory().getConnection();
		conn.expire(SafeEncoder.encode(key), seconds);
	}

	public void pExpire(String key, long millis) {
		@Cleanup
		RedisConnection conn = redisTemplate.getConnectionFactory().getConnection();
		conn.pExpire(SafeEncoder.encode(key), millis);
	}

	public Long acquire(String key, Integer maxCount, Long millisecond) {
		return (Long) redisTemplate.execute(redisScript, Collections.singletonList(key), parseToString(maxCount), parseToString(millisecond));
	}

	private <T> String parseToString(T t) {
		return Optional.ofNullable(t).map(Objects::toString).orElse(StringUtils.EMPTY);
	}
}
