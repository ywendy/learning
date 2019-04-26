package com.ypb.concurrent.stage1.chapter4;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: DaemonThread2
 * @Description: 线程中在创建线程
 * @author yangpengbing
 * @date 2019-04-09-11:17
 * @version V1.0.0
 *
 */
@Slf4j
public class DaemonThread2 {

	public static void main(String[] args) {
		// 用户线程执行完毕，daemon守护线程自动退出。
		Thread t1 = new Thread(() -> {
			Thread innerThread = new Thread(() -> {
				try {
					while (true) {
						System.out.println("Do something for health check.");
						Thread.sleep(1_000);
					}
				} catch (Exception e) {
					log.debug(e.getMessage(), e);
				}
			}, "InnerThread");

			innerThread.setDaemon(Boolean.TRUE);
			innerThread.start();

			try {
				Thread.sleep(1_000);
				System.out.println("I thread finsish done.");
			} catch (Exception e) {
				log.debug(e.getMessage(), e);
			}

		}, "DaemonThread");

		t1.setDaemon(Boolean.TRUE);
		t1.start();
	}
}
