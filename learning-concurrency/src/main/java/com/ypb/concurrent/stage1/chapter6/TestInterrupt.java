package com.ypb.concurrent.chapter6;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: TestInterrupt
 * @Description: 测试Interrupt方法
 * @author yangpengbing
 * @date 2019-04-10-10:55
 * @version V1.0.0
 *
 */
@Slf4j
public class TestInterrupt {

	private Runnable runnable = new Runnable() {

		@Override
		public void run() {
			int i = 0;
			try {
				while (i < 1000) {
					Thread.sleep(500);
					System.out.println(i++);
				}
			} catch (InterruptedException e) {
				log.debug(e.getMessage(), e);
			}
		}
	};

	public static void main(String[] args) {
		TestInterrupt interrupt = new TestInterrupt();
		Thread t = new Thread(interrupt.runnable);
		
		System.out.println("main");
		t.start();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			log.debug(e.getMessage(), e);
		}

		t.interrupt();
	}
}
