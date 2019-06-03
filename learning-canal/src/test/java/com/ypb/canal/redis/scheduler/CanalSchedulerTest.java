package com.ypb.canal.redis.scheduler;

import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CanalSchedulerTest {

	@Autowired
	private CanalScheduler canalScheduler;

	@Test
	public void fetch() throws InterruptedException {
		while (true) {
			canalScheduler.fetch();

			TimeUnit.SECONDS.sleep(2L);
		}
	}
}