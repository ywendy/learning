package com.ypb.lru;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 * @author yangpengbing
 * @version V1.0.0
 * @ClassName: SimpleLRUCache
 * @Description: 基于LinekHashMap实现lru
 * @date 2019-05-14-11:36
 */
public class SimpleLRUCache<K, V> implements LRUCache<K, V> {

	private static final int MAX_CACHE_SIZE = 1024;
	private static final float DEFAULT_LOAD_FACTOR = 0.75f;

	private final int cacheSize;
	private LinkedHashMap<K, V> map;

	public SimpleLRUCache() {
		this(MAX_CACHE_SIZE);
	}

	/**
	 * boolean的accessOrder，这个参数默认是false，true是按照访问顺序，当为false是，按照插入顺序
	 * @param cacheSize
	 */
	public SimpleLRUCache(int cacheSize) {
		this.cacheSize = cacheSize;
		int capacity = (int) (Math.ceil(cacheSize / DEFAULT_LOAD_FACTOR) + 1);

		map = new LinkedHashMap<K, V>(capacity, DEFAULT_LOAD_FACTOR, Boolean.TRUE) {
			@Override
			protected boolean removeEldestEntry(Entry eldest) {
				return size() > cacheSize;
			}
		};
	}

	@Override
	public synchronized void put(K key, V value) {
		map.put(key, value);
	}

	@Override
	public synchronized V get(K key) {
		return map.get(key);
	}

	@Override
	public synchronized void remove(K key) {
		map.remove(key);
	}

	@Override
	public synchronized Collection<?> getAll() {
		return map.entrySet();
	}

	/**************************** 学习笔记(2019年5月16日) ******************************/
//	TreeMap和LinkedHashMap都是有序的，HashMap是无序的，
//	LinkedHashMap是基于元素进入集合地顺序或者被访问的先后顺序进行排序
//	TreeMap是基于元素的固有顺序(由Comparator或者Comparable确定)，是根据元素的key进行排序
}
