package com.ypb.concurrent.stage1.chapter4;

import java.util.Optional;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: ThreadSimpleAPI2
 * @Description: 线程的优先级
 * @author yangpengbing
 * @date 2019-04-09-11:30
 * @version V1.0.0
 *
 */
@Slf4j
public class ThreadSimpleAPI2 {

	public static void main(String[] args) {
		Thread t1 = createThread();
		t1.setPriority(Thread.MAX_PRIORITY);

		Thread t2 = createThread();
		t2.setPriority(Thread.NORM_PRIORITY);

		Thread t3 = createThread();
		t3.setPriority(Thread.MIN_PRIORITY);

		t1.start();
		t2.start();
		t3.start();
	}

	private static Thread createThread() {
		return new Thread(() -> {
			IntStream.range(0, 10000)
					.forEach(i -> Optional.of(String.join("-", Thread.currentThread().getName(), "Index", "" + i))
							.ifPresent(System.out::println));
		});
	}
}
