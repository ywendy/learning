package com.ypb.concurrent.chapter4;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: ThreadSimpleAPI
 * @Description: Thread的基本API
 * @author yangpengbing
 * @date 2019-04-09-11:25
 * @version V1.0.0
 *
 */
@Slf4j
public class ThreadSimpleAPI {

	public static void main(String[] args) {
		Thread t1 = new Thread(() -> {
			Optional.of("hello").ifPresent(System.out::println);
			try {
				Thread.sleep(10_000);
			} catch (Exception e) {
				log.debug(e.getMessage(), e);
			}
		}, "t1");

		t1.start();

		Optional.of(t1.getName()).ifPresent(System.out::println);
		Optional.of(t1.getId()).ifPresent(System.out::println);
		Optional.of(t1.getPriority()).ifPresent(System.out::println);
	}
}
