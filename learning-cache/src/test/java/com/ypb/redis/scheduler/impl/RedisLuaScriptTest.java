package com.ypb.redis.scheduler.impl;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @ClassName: RedisLuaScriptTest
 * @Description: 测试lua脚本
 * @author yangpengbing
 * @date 2019-05-13-10:53
 * @version V1.0.0
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisLuaScriptTest {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Test
	public void test_check_and_set() {
		DefaultRedisScript<Boolean> rs = new DefaultRedisScript<>();
		rs.setResultType(Boolean.class);
		rs.setScriptSource(new ResourceScriptSource(new ClassPathResource("/lua/check_and_set.lua")));

		Boolean flag = redisTemplate.execute(rs, Lists.newArrayList("ypb"), new Object[]{"111", "222"});

		System.out.println("flag = " + flag);
	}

	@Test
	public void test_string_set() {
		RedisScript rs = initRedisScript("string_set.lua", String.class);

		List<String> ypb = Lists.newArrayList("ypb", "111");
		String str = (String) redisTemplate.execute(rs, ypb);

		System.out.println("str = " + str);
	}

	private RedisScript<?> initRedisScript(String scriptName, Class clazz) {
		String path = "/lua/" + scriptName;

		DefaultRedisScript<?> rs = new DefaultRedisScript<>();
		rs.setResultType(clazz);
		rs.setScriptSource(new ResourceScriptSource(new ClassPathResource(path)));

		return rs;
	}
}
