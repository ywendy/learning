package com.ypb.redis.scheduler.impl;

import com.ypb.CacheApplicationMain;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CacheApplicationMain.class)
public class RedisTaskSchedulerImplIntegrationTest {

	@Autowired
	private RedisTaskSchedulerImpl scheduler;
	@Autowired
	private StubbedClock stubbedClock;
	private int times = 1000000;
	@Autowired
	private RedisTemplate redisTemplate;
	private CountDownLatch latch;

	private static final Random random = new Random(System.currentTimeMillis());

	@Before
	public void setUp(){
		latch = new CountDownLatch(2);
	}

	@Test
	public void schedule() {
		String taskID = "1111";

		stubbedClock.is("20190508 12:00");
		Calendar calendar = stubbedClock.in(1, TimeUnit.SECONDS);

		scheduler.schedule(taskID, calendar);
	}

	/**
	 * 使用CountDownLatch执行
	 * @throws InterruptedException
	 */
	@Test
	public void multiThreadSchedule() throws InterruptedException {
		String dateTimeStr = "20180508 14:50";

		stubbedClock.is(dateTimeStr);

		new SchedulePutThread(latch).start();

		sleep(1000);

		latch.await();
	}

	private void sleep(long timeout) {
		try {
			TimeUnit.MILLISECONDS.sleep(timeout);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void putTask() {
		IntStream.rangeClosed(1, times).forEach(i -> {
			String taskID = StringUtils.EMPTY + i;
			Calendar calendar = stubbedClock.in(random(i), TimeUnit.MILLISECONDS);

			scheduler.schedule(taskID, calendar);
		});
	}

	private int random(int i) {
		return random.nextInt(i);
	}

	private class SchedulePutThread extends Thread {

		private final CountDownLatch latch;

		public SchedulePutThread(CountDownLatch latch) {
			this.latch = latch;
		}

		@Override
		public void run() {
			try {
				putTask();
			}finally {
				latch.countDown();
			}
		}
	}
}
