package com.ypb.timer;

import org.junit.Test;

public class TimerTest {

	@Test
	public void testTimer() {
		Timer timer = Timer.getInstance();

		Integer times = 1000;
		for (Integer i = 0; i < times; i++) {
			long delayMs = (long) (Math.random() * 100000L);

			Integer finalI = i;
			TimedTask task = new TimedTask(delayMs, () -> runnableTask(finalI));
			timer.addTask(task);
		}

		while (true) {

		}
	}

	private void runnableTask(final Integer finalI) {
		System.out.println(Thread.currentThread().getName() + " execute " + finalI);
	}
}