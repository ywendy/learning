package com.ypb.concurrent.stage3.chapter1;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author yangpengbing
 * @version 1.0.0
 * @className AtomicIntegerFiledUpdaterTest
 * @description AtomicIntegerFiledUpdater主要是用来操作对象的，使其具有原子性
 * @date 23:01 2019/5/15
 */
public class AtomicIntegerFiledUpdaterTest {

    private static final AtomicIntegerFieldUpdater<TestMe> updater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "counter");
    private static final TestMe me = new TestMe();

    public static void main(String[] args) {

        int times = 2;
        for (int i = 0; i < times; i++) {
            new Thread(AtomicIntegerFiledUpdaterTest::runTask).start();
        }
    }

    private static void runTask() {
        final int max = 20;

        for (int i = 0; i < max; i++) {
            int value = updater.getAndIncrement(me);

            System.out.println(Thread.currentThread().getName() +" => "+ value);
        }
    }

    private static class TestMe {
        volatile int counter;
    }
}
