package com.ypb.timer.v2;

import com.google.common.base.Stopwatch;
import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SystemTimerTest {

	private static final Random random = new Random(System.currentTimeMillis());

	public static void main(String[] args) throws InterruptedException {
		long timeoutMs = 200L;
		SystemTimer timer = new SystemTimer(timeoutMs);

		test1(timer);

//		test2(timeoutMs, timer);
	}

	private static void test1(SystemTimer timer) throws InterruptedException {
		TimerTask task = new TimerTask(20000);
		timer.add(task);

		TimeUnit.MILLISECONDS.sleep(10000);

		timer.cancel(task);

		while (timer.size() > BigDecimal.ZERO.intValue()) {
			System.out.println("timer.size() = " + timer.size());
			TimeUnit.MILLISECONDS.sleep(200);
		}

		timer.shutdown();
	}

	private static void test2(long timeoutMs, SystemTimer timer) throws InterruptedException {
		Stopwatch stopwatch = Stopwatch.createStarted();

		int times = 10000 * 10000;
		for (int i = 0; i < times; i++) {
			timer.add(new TimerTask(random.nextInt(100000)));
		}

		while (timer.size() > BigDecimal.ZERO.intValue()) {
			System.out.println("timer.size() = " + timer.size());
			TimeUnit.MILLISECONDS.sleep(timeoutMs);
		}

		System.out.println("timer.size() finish = " + timer.size());

		stopwatch.stop();
		System.out.println("stopwatch.toString() = " + stopwatch.toString());
		timer.shutdown();
	}
}
