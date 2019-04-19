package com.ypb.concurrent.chapter6;

import com.google.common.base.Stopwatch;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: ThreadCloseForce
 * @Description: 强制关闭线程
 * @author yangpengbing
 * @date 2019-04-10-11:51
 * @version V1.0.0
 *
 */
@Slf4j
public class ThreadCloseForce {

	public static void main(String[] args) {
		ThreadService service = new ThreadService();

		Stopwatch stopwatch = Stopwatch.createStarted();
		service.execute(() -> {

//			while (true) {
//
//			}

			try {
				Thread.sleep(5_000);
			} catch (InterruptedException e) {
				log.debug(e.getMessage(), e);
			}
		});

		service.shutdown(10_000);
		stopwatch.stop();
		System.out.println(stopwatch.toString());
		System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));
	}
}
