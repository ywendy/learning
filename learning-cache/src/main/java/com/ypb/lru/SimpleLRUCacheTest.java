package com.ypb.lru;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: SimpleLRUCacheTest
 * @Description: https://www.toutiao.com/a6689968224273957387/?tt_from=weixin&utm_campaign=client_share&wxshare_count=1&timestamp=1557797012&app=news_article&utm_source=weixin&utm_medium=toutiao_android&req_id=201905140923320100230191005238321&group_id=6689968224273957387
 * @author yangpengbing
 * @date 2019-05-14-15:19
 * @version V1.0.0
 *
 */
@Slf4j
public class SimpleLRUCacheTest {

	private static final List<Thread> threads = Lists.newArrayList();
	private static final LRUCache<Integer, Integer> cache = new SimpleLRUCache<>();
	private static final AtomicInteger ai = new AtomicInteger(1000000);
	private static final Random random = new Random(System.currentTimeMillis());

	public static void main(String[] args) {
		IntStream.rangeClosed(1, 1).forEach(i -> threads.add(new Thread(SimpleLRUCacheTest::runTaskWithPut, "put-" + i)));
		IntStream.rangeClosed(1, 1).forEach(i -> threads.add(new Thread(SimpleLRUCacheTest::runTaskWithGet, "get-" + i)));
		IntStream.rangeClosed(1, 1).forEach(i -> threads.add(new Thread(SimpleLRUCacheTest::runTaskWithGetAll, "getAll-" + i)));

		for (Thread thread : threads) {
			thread.start();
		}
	}

	private static void runTaskWithGetAll() {
		while (true) {
			try {
				TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
			} catch (InterruptedException e) {
				log.debug(e.getMessage(), e);
			}

			System.out.println("cache.getAll() = " + cache.getAll());
		}
	}

	private static void runTaskWithGet() {
		int value;
		while ((value = ai.get()) >= 0) {
			System.out.println(Thread.currentThread().getName() + ", value = " + cache.get(value));
		}
	}

	private static void runTaskWithPut() {
		int value;
		while ((value = ai.decrementAndGet()) >= 0) {
			cache.put(value, value);

			System.out.println(Thread.currentThread().getName() + ", value = " + value);
		}
	}
}
