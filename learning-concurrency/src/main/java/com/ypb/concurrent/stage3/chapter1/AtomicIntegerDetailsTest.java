package com.ypb.concurrent.stage3.chapter1;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerDetailsTest {

    private static final AtomicInteger ai = new AtomicInteger();

    public static void main(String[] args) {
        AtomicInteger ai = new AtomicInteger();
        System.out.println("ai.get() = " + ai.get());

        ai = new AtomicInteger(10);
        System.out.println("ai.get() = " + ai.get());

        ai.set(12);
        System.out.println("ai.get() = " + ai.get());

        ai.lazySet(13);
        System.out.println("ai.get() = " + ai.get());

        ai = new AtomicInteger(10);
        int result = ai.getAndAdd(10);
        System.out.println("result = " + result);
        System.out.println("ai.get() = " + ai.get());

        new Thread(AtomicIntegerDetailsTest::runTask).start();
        new Thread(AtomicIntegerDetailsTest::runTask).start();

        ai = new AtomicInteger(10);
        boolean flag = ai.compareAndSet(12, 20);
        System.out.println("ai.get() = " + ai.get());
        System.out.println("flag = " + flag);
    }

    private static void runTask() {
        for (int i = 0; i < 10; i++) {
            int get = ai.addAndGet(1);
            System.out.println("get = " + get);
        }
    }
}
