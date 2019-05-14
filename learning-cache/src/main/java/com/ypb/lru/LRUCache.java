package com.ypb.lru;

import java.util.Collection;

public interface LRUCache<K, V> {

	void put(K key, V value);

	V get(K key);

	void remove(K key);

	Collection<?> getAll();
}
