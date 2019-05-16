package com.ypb.concurrent.stage3.chapter1;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AIFUTest {

    private volatile int counter;

    private final AtomicIntegerFieldUpdater<AIFUTest> updater;

    public AIFUTest() {
        this.updater = AtomicIntegerFieldUpdater.newUpdater(AIFUTest.class, "counter");
    }

    public void update(int newValue) {
        updater.compareAndSet(this, counter, newValue);
    }

    public int get() {
        return counter;
    }

    public static void main(String[] args) {
        AIFUTest test = new AIFUTest();
        test.update(10);

        System.out.println(test.get());
    }
}
