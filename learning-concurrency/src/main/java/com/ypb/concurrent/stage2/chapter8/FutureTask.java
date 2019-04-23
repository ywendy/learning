package com.ypb.concurrent.stage2.chapter8;

@FunctionalInterface
public interface FutureTask<T> {

    T call();
}
