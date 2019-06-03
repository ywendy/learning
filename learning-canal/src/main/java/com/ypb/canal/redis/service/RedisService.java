package com.ypb.canal.redis.service;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	public void hmset(String key, Map<String, String> map) {
		redisTemplate.opsForHash().putAll(key, map);
	}
}
