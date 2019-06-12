package com.ypb.token.utils;

import java.util.Collections;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jredis.JredisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Slf4j
@Component
public class RedisLock {

	private static final Long release_success = 1L;
	private static final String lock_success = "OK";
	private static final String set_if_not_exist = "NX";
	/**
	 * 当前设置 过期时间单位, EX = seconds; PX = milliseconds
 	 */
	private static final String set_with_expire_time = "EX";
	private static final String release_lock_lua_script = "if redis.call('get', KEYS[1]) == ARGV[1] then redis.call('del', KEYS[1]) else return 0 end";

	@Autowired
	private StringRedisTemplate redisTemplate;

	/**
	 * 该加锁方法仅针对单实例 Redis 可实现分布式加锁 对于 Redis 集群则无法使用
	 * @param key
	 * @param clientId
	 * @param seconds
	 * @return
	 */
	public boolean tryLock(String key, String clientId, int seconds) {
		return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
			Jedis jedis = (Jedis) redisConnection.getNativeConnection();

			String result = jedis.set(key, clientId, set_if_not_exist, set_with_expire_time, seconds);

			if (lock_success.equals(result)) {
				return Boolean.TRUE;
			}

			return Boolean.FALSE;
		});
	}

	public boolean releaseLock(String key, String clientIp) {
		return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
			Jedis jedis = (Jedis) redisConnection.getNativeConnection();
			Long result = (Long) jedis.eval(release_lock_lua_script, Collections.singletonList(key), Collections.singletonList(clientIp));

			if (release_success.equals(result)) {
				return Boolean.TRUE;
			}

			return Boolean.FALSE;
		});
	}
}
