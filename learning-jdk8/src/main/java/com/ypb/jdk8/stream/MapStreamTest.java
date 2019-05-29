package com.ypb.jdk8.stream;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MapStreamTest {

	public static void main(String[] args) {
		Map<Integer, String> map = initMap(10);

		testReduce(map);

//		testMerge(map);

//		testRemove(map);

//		testCompute(map);

//		printConsole(map);
	}

	private static void testReduce(Map<Integer, String> map) {
		ConcurrentHashMap<Integer, String> concurrentMap = new ConcurrentHashMap<>(map);

		int key = 11;
		String reduce = concurrentMap.reduce(key, (i1, s1) -> i1 + "###" + s1, (i2, s2) -> i2 + "&&&" + s2);

		System.out.println("reduce = " + reduce);
	}

	/**
	 * 如果key在map中不存在，会将key和value保存到map中
	 * 如果key在map中存在，会将key对应的value作为oldValue和传入的value，应用第三个参数的BIFunction函数，将其返回的值作为newValue，替换就的value
	 * @param map
	 */
	private static void testMerge(Map<Integer, String> map) {
		int key = 19;
		String value = "val" + key;
		System.out.println(
				"map.merge(key, map.get(key), String::concat) = " + map.merge(key, value, String::concat));
		printSplitLine();

		value = "bam";
		System.out.println("map.merge(key, value, String::concat) = " + map.merge(key, value, String::concat));
	}

	private static void testRemove(Map<Integer, String> map) {
		int key = 3;
		map.remove(key, "val3-3");
		System.out.println("map.get(3) = " + map.get(key));
		printSplitLine();

		map.remove(key, "val3");
		System.out.println("map.get(3) = " + map.get(key));
	}

	private static void testCompute(Map<Integer, String> map) {
		int index = 3;
		// 存在就计算，第二个参数是BIFunction
		map.computeIfPresent(index, (key, value) -> value + "-" + key);
		System.out.println("map.get(3) = " + map.get(index));
		printSplitLine();

		index = 9;
		System.out.println("map.containsKey(9) = " + map.containsKey(index));
		map.computeIfPresent(index, (key, value) -> null);
		System.out.println("map.containsKey(9) = " + map.containsKey(index));
		printSplitLine();

		index = 23;
		// 如果不存在就计算，第二个参数是Function
		map.computeIfAbsent(index, key -> "val" + key);
		System.out.println("map.get(23) = " + map.get(index));
		printSplitLine();

		index = 3;
		map.computeIfAbsent(index, key -> "bam" + key);
		System.out.println("map.get(3) = " + map.get(index));
	}

	private static void printSplitLine() {
		System.out.println("------------------------------------");
	}

	private static void printConsole(Map<Integer, String> map) {
		map.forEach((key, value) -> {
			String msg = "key=" + key + ", value=" + value;
			System.out.println(msg);
		});
	}

	private static Map<Integer, String> initMap(int times) {
		Map<Integer, String> map = Maps.newHashMapWithExpectedSize(times);
		for (int i = 0; i < times; i++) {
			map.putIfAbsent(i, "val" + i);
		}

		return map;
	}

}
