package com.ypb.concurrent.stage2.chapter10;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author yangpengbing
 * @version 1.0.0
 * @className ThreadLocalSimulator
 * @description 模拟ThreadLocal
 * @date 22:27 2019/4/24
 */
public class ThreadLocalSimulator<T> {

    private final Map<Thread, T> storages = Maps.newHashMap();

    public void set(T t) {
        synchronized (this) {
            Thread thread = Thread.currentThread();
            storages.put(thread, t);
        }
    }

    public T get() {
        synchronized (this) {
            Thread thread = Thread.currentThread();

            return storages.getOrDefault(thread, initialValue());
        }
    }

    protected T initialValue() {
        return null;
    }
}
