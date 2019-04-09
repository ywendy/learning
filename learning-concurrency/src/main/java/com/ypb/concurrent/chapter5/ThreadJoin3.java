package com.ypb.concurrent.chapter5;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: ThreadJoin3
 * @Description: 联系
 * @author yangpengbing
 * @date 2019-04-09-15:37
 * @version V1.0.0
 *
 */
@Slf4j
public class ThreadJoin3 {

	public static void main(String[] args) throws InterruptedException {
		long start = System.currentTimeMillis();

		Thread t1 = new Thread(new CapturedRunnable("M1", 10000L));
		Thread t2 = new Thread(new CapturedRunnable("M2", 30000L));
		Thread t3 = new Thread(new CapturedRunnable("M3", 15000L));

		t1.start();
		t2.start();
		t3.start();

		t1.join();
		t2.join();
		t3.join();

		long endTime = System.currentTimeMillis();
		System.out.printf("save data begin timestamp is:%s, end timestamp is:%s\n", start, endTime);
	}
}

@Slf4j
class CapturedRunnable implements Runnable {

	private String machineName;
	private long spendTime;

	public CapturedRunnable(String machineName, long spendTime) {
		this.machineName = machineName;
		this.spendTime = spendTime;
	}

	@Override
	public void run() {
		// do the really capture data.
		try {
			Thread.sleep(spendTime);
			System.out.printf(machineName + " completed data capture at timestamp [%s] and successfully.\n",
					System.currentTimeMillis());
		} catch (InterruptedException e) {
			log.debug(e.getMessage(), e);
		}
	}
}
