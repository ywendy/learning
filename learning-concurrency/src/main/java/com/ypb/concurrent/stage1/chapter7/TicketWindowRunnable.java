package com.ypb.concurrent.chapter7;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: TicketWindowRunnable
 * @Description: 售票大厅Runnable
 * @author yangpengbing
 * @date 2019-04-10-15:05
 * @version V1.0.0
 *
 */
@Slf4j
public class TicketWindowRunnable implements Runnable {

	private int index = 1;

	private static final int MAX = 5000;
	private final Object MONITOR = new Object();

	@Override
	public void run() {
		try {
			while (true) {
				synchronized (MONITOR) {
					if (index > MAX) {
						break;
					}

					Thread.sleep(5);

					System.out.println(Thread.currentThread() + " 的号码是：" + (index++));
				}
//				会多输出501和502
//				System.out.println(Thread.currentThread() + " 的号码是：" + (++index));
			}
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}
	}
}
