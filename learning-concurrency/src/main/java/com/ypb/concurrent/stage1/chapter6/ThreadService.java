package com.ypb.concurrent.stage1.chapter6;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: ThreadService
 * @Description: 使用守护线程和线程中断和join()方法的方式关闭线程
 * @author yangpengbing
 * @date 2019-04-10-11:53
 * @version V1.0.0
 *
 */
@Slf4j
public class ThreadService {

	private boolean finished = false;
	private Thread executeThread;

	/**
	 * 执行任务
	 * @param task
	 */
	public void execute(Runnable task) {
		// 创建一个线程，里面在创建一个守护线程执行task
		executeThread = new Thread(() -> {
			Thread runner = new Thread(task);

			runner.setDaemon(Boolean.TRUE);
			runner.start();

			try {
				// join()方法是等到runner线程执行完毕后，在执行当前线程后面的代码
				runner.join();
				finished = Boolean.TRUE;
			} catch (InterruptedException e) {
				log.debug(e.getMessage(), e);
			}
		});

		executeThread.start();
	}

	/**
	 * 关闭线程， 等到millis毫秒
	 * @param millis
	 */
	public void shutdown(long millis) {
		long startTime = System.currentTimeMillis();
		try {
			while (!finished) {
				if (System.currentTimeMillis() - startTime >= millis) {
					System.out.println("task time out need shutdown it.");
					executeThread.interrupt();

					break;
				}

				Thread.sleep(50);
			}
		} catch (InterruptedException e) {
			log.debug(e.getMessage(), e);
			System.out.println("execute thread by interrupt");
		}

		finished = false;
	}
}
