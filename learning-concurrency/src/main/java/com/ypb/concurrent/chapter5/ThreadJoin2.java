package com.ypb.concurrent.chapter5;

import java.util.Optional;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: ThreadJoin2
 * @Description: Thread的join方法测试
 * @author yangpengbing
 * @date 2019-04-09-15:19
 * @version V1.0.0
 *
 */
@Slf4j
public class ThreadJoin2 {

	public static void main(String[] args) throws InterruptedException {
		/*Thread t1 = new Thread(() -> {
			try {
				System.out.println("t1 is running.");
				Thread.sleep(10_000);
				System.out.println("t1 is done.");
			} catch (Exception e) {
				log.debug(e.getMessage(), e);
			}
		});

		t1.start();
		t1.join(100, 10);

		Optional.of("All of tasks finish done.").ifPresent(System.out::println);
		IntStream.range(1, 1000)
				.forEach(i -> Optional.of(Thread.currentThread().getName() + "->" + i).ifPresent(System.out::println));

		*/

		Thread t1 = new Thread(() -> {
			System.out.println("t1 is running.");
			while (true) {
				System.out.println(Thread.currentThread().isInterrupted());
			}
		});

		t1.start();

		Thread t2 = new Thread(() -> {
			try {
				System.out.println("t2 is running.");
				Thread.sleep(10_000);
				t1.interrupt();
				System.out.println("t1 interrupt");
			} catch (InterruptedException e) {
				log.debug(e.getMessage(), e);
			}
		});

		t2.start();

		t1.join(4_000);
	}
}
