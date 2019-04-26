package com.ypb.concurrent.stage1.chapter6;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: ThreadCloseGraceful2
 * @Description: 使用线程interrupt()方法，优雅的停止线程
 * @author yangpengbing
 * @date 2019-04-10-11:44
 * @version V1.0.0
 *
 */
@Slf4j
public class ThreadCloseGraceful2 {

	public static void main(String[] args) throws InterruptedException {
		Runnable worker = new Worker();
		Thread t = new Thread(worker);

		t.start();

		Thread.sleep(10_000);

		t.interrupt();
	}

	private static class Worker implements Runnable {

		@Override
		public void run() {
			int counter = 0;
			try {
				while (true) {
					if (Thread.interrupted()) {
						break;
					}

					System.out.println("worker running " + (++counter));
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				log.debug(e.getMessage(), e);
			}
		}
	}
}
