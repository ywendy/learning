package com.ypb.timer;

public class TimerTest {

	public static void main(String[] args) {
		Timer timer = Timer.getInstance();

		Integer times = 100;
		for (Integer i = 0; i < times; i++) {
			long delayMs = (long) (Math.random() * 100000L);

			Integer finalI = i;
			TimedTask task = new TimedTask(delayMs, () -> System.out.println(finalI));
			timer.addTask(task);
		}
	}
}