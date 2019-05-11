package com.ypb.concurrent.stage1.chapter9;

import com.sun.org.apache.regexp.internal.RE;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProduceAndConsumerVersion2 {

    private int count;
    private final Object lock = new Object();
    private volatile boolean produce = false;

    public void produce() throws InterruptedException {
        synchronized (lock) {
            if (produce) {
                lock.wait();
            }

            System.out.println("p -->" + ++count);
            lock.notify();
            produce = true;
        }
    }
    
    public void consumer() throws InterruptedException {
        synchronized (lock) {
            if (!produce) {
                lock.wait();
            }

            System.out.println("c -->" + count);
            lock.notify();
            produce = false;
        }
    }

    public static void main(String[] args) {
        ProduceAndConsumerVersion2 pc = new ProduceAndConsumerVersion2();

        // test2存在重复消费的问题和一直等待， 因为使用的是notifyAll
        test2(pc);

//        test1(pc);

    }

    private static void test2(ProduceAndConsumerVersion2 pc) {
        Arrays.asList("a", "b").forEach(s -> new Thread(()->{
            while (true) {
                try {
                    pc.produce();
                } catch (InterruptedException e) {
                    log.debug(e.getMessage(), e);
                }
            }
        }, s).start());

        Arrays.asList("c", "d", "e").forEach(s -> new Thread(()->{
            while (true) {
                try {
                    pc.consumer();
                } catch (InterruptedException e) {
                    log.debug(e.getMessage(), e);
                }
            }
        }, s).start());
    }

    private static void test1(ProduceAndConsumerVersion2 pc) {
        new Thread(()->{
            while (true) {
                try {
                    pc.produce();
                } catch (InterruptedException e) {
                    log.debug(e.getMessage(), e);
                }
            }
        }).start();

        new Thread(()->{
            while (true) {
                try {
                    pc.consumer();
                } catch (InterruptedException e) {
                    log.debug(e.getMessage(), e);
                }
            }
        }).start();
    }
}
