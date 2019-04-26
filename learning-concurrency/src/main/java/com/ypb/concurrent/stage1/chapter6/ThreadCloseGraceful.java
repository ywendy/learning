package com.ypb.concurrent.stage1.chapter6;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: ThreadCloseGraceful
 * @Description: 优雅的关闭线程 思路是使用volatile关键字，设置一个flag对所有线程可见
 * @author yangpengbing
 * @date 2019-04-10-11:35
 * @version V1.0.0
 *
 */
@Slf4j
public class ThreadCloseGraceful {

	public static void main(String[] args) throws InterruptedException {
		Worker worker = new Worker();
		new Thread(worker).start();

		Thread.sleep(10_000);

		worker.shutdown();
	}

	private static class Worker implements Runnable {

		private volatile boolean start = true;

		public void shutdown() {
			this.start = false;
		}

		@Override
		public void run() {
			int counter = 0;
			try {
				while (start) {
					System.out.println("worker running." + (++counter));
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				log.debug(e.getMessage(), e);
			}
		}
	}
}
