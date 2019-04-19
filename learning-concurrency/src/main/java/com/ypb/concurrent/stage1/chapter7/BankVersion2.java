package com.ypb.concurrent.chapter7;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: BankVersion2
 * @Description: 银行大厅
 * @author yangpengbing
 * @date 2019-04-10-16:16
 * @version V1.0.0
 *
 */
@Slf4j
public class BankVersion2 {

	public static void main(String[] args) {
		Stopwatch stopwatch = Stopwatch.createStarted();

		final TicketWindowRunnable runnable = new TicketWindowRunnable();

		Thread t1 = new Thread(runnable,"1号柜台");
		Thread t2 = new Thread(runnable,"2号柜台");
		Thread t3 = new Thread(runnable,"3号柜台");

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
}
