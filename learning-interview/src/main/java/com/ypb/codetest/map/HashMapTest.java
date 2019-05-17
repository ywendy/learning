package com.ypb.codetest.map;

import org.springframework.util.StopWatch;

public class HashMapTest {

	public static void main(String[] args) {
//		testHashMapSizeOfTwoMultiple();
		testModulesAndBitOperator();
	}

	/**
	 * 测试取模运算和按位运算的性能
	 */
	private static void testModulesAndBitOperator() {
		StopWatch watch = new StopWatch();
		watch.start("取模运算");

		int a = 0;
		int times = Integer.MAX_VALUE;
		for (int i = 0; i < times; i++) {
			a = i % 1024;
		}

		watch.stop();
		System.out.println("a = " + a);
		System.out.println("watch.prettyPrint() = " + watch.prettyPrint());

		watch = new StopWatch();
		watch.start("按位运算");

		a = 0;
		for (int i = 0; i < times; i++) {
			a = i & (1024 - 1);
		}

		watch.stop();
		System.out.println("a = " + a);
		System.out.println("watch.prettyPrint() = " + watch.prettyPrint());
	}

	/**
	 * 测试HashMap扩容为什么是2的倍数, 其实主要是优化了获取数组索引index的算法，用到的，hash % length, 替换成 hash & (length - 1)
	 */
	private static void testHashMapSizeOfTwoMultiple() {
		int hash = 24;
		int length = 16;

		System.out.println("hash % length = " + (hash % length));

		System.out.println("hash & (length - 1) = " + (hash & (length - 1)));
	}

}
