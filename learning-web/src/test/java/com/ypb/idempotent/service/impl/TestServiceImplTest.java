package com.ypb.idempotent.service.impl;

import com.ypb.idempotent.common.RedisService;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TestServiceImplTest {

	@Autowired
	private RedisService redisService;

	@Test
	public void test_lua_script() {
		String key = "111";
		Integer maxCount = 1000;
		Long millisecond = 60 * 1000L;

		for (Integer i = 0; i < maxCount; i++) {
			Long acquire = redisService.acquire(key, maxCount, millisecond);

			System.out.println("acquire = " + acquire);
		}
	}

	@Test
	public void test() {
		long millis = 10L;

		TimeUnit unit = TimeUnit.SECONDS;

		long toMillis = unit.toMillis(millis);
		System.out.println("toMillis = " + toMillis);
	}

}