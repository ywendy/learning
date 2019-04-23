package com.ypb.concurrent.stage2.chapter8;

/**
 * @author yangpengbing
 * @version 1.0.0
 * @className Future
 * @description future设置模式
 * @date 21:21 2019/4/23
 */
@FunctionalInterface
public interface Future<T> {

    T get() throws InterruptedException;
}
