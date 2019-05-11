package com.ypb.concurrent.stage1.chapter9;

import com.sun.org.apache.regexp.internal.RE;

public class ProduceAndConsumerVersion1 {

    private int count;
    private final Object lock = new Object();

    public void produce() {
        synchronized (lock) {
            System.out.println("p -->" + ++count);
        }
    }

    public void consumer() {
        synchronized (lock) {
            System.out.println("c -->" + count);
        }
    }

    public static void main(String[] args) {
        ProduceAndConsumerVersion1 pc = new ProduceAndConsumerVersion1();

        new Thread(() -> {
            while (true) {
                pc.produce();
            }
        }).start();

        new Thread(()->{
            while (true) {
                pc.consumer();
            }
        }).start();
    }
}
