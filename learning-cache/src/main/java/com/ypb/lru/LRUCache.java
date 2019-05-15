package com.ypb.lru;

import java.util.Collection;

/**
 * LRU(Least Recently Used, 也就是最近最少使用)
 * @param <K>
 * @param <V>
 */
public interface LRUCache<K, V> {

	void put(K key, V value);

	V get(K key);

	void remove(K key);

	Collection<?> getAll();
}
