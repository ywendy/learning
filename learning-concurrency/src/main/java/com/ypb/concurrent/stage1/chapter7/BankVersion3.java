package com.ypb.concurrent.stage1.chapter7;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: BankVersion3
 * @Description: 银行大厅 version3版本
 * @author yangpengbing
 * @date 2019-04-10-16:28
 * @version V1.0.0
 *
 */
@Slf4j
public class BankVersion3 {

	public static void main(String[] args) {
		Stopwatch stopwatch = Stopwatch.createStarted();

		final Runnable runnable = new SynchronizedRunnable();

		Thread t1 = new Thread(runnable, "一号柜台");
		Thread t2 = new Thread(runnable, "二号柜台");
		Thread t3 = new Thread(runnable, "三号柜台");

		t1.start();
		t2.start();
		t3.start();

		try {
			t1.join();
			t2.join();
			t3.join();
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}

		stopwatch.stop();
		System.out.println(stopwatch.toString());
	}

	private static class SynchronizedRunnable implements Runnable {

		private int counter = 1;
		private static final int MAX = 5000;

		@Override
		public void run() {
			try {
				while (true) {
					if (ticket()) {
						break;
					}
				}
			} catch (Exception e) {
				log.debug(e.getMessage(), e);
			}
		}

		private boolean ticket() throws InterruptedException {
			synchronized (this) {
				if (counter > MAX) {
					return true;
				}

				Thread.sleep(5);
				System.out.println(Thread.currentThread() + " 的号码是: " + (counter++));
				return false;
			}
		}

		//		@Override
//		public synchronized void run() {
//			try {
//				while (true) {
//					if (counter > MAX) {
//						break;
//					}
//
//					Thread.sleep(5);
//
//					System.out.println(Thread.currentThread() + " 的号码是：" + (counter++));
//				}
//			} catch (InterruptedException e) {
//				log.debug(e.getMessage(), e);
//			}
//		}
	}
}
