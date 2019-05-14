package com.ypb.lru;

import java.util.Random;

public class SimpleLRUCacheTest2 {

	public static void main(String[] args) {
		LRUCache<String, String> cache = new SimpleLRUCache<>(10);
		final Random random = new Random(System.currentTimeMillis());

		String prefixKey = "yangpengbing";

		int times = 8;
		for (int i = 0; i < times; i++) {
			String key = prefixKey + i;

			cache.put(key, "" + i);
		}

		System.out.println("cache.getAll() = " + cache.getAll());

		for (int i = 0; i < 2; i++) {
			int next = random.nextInt(times);

			String key = prefixKey + next;
			System.out.println("key = " + key + ", value = " + cache.get(key));
		}

		System.out.println("cache.getAll() = " + cache.getAll());

		times = 12;
		for (int i = 8; i < times; i++) {
			String key = prefixKey + i;

			cache.put(key, "" + i);
		}

		System.out.println("cache.getAll() = " + cache.getAll());
	}

}
