package com.ypb.concurrent.stage1.chapter9;

import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProcuceAndConsumerVersion3 {

    private int count;
    private final Object lock = new Object();
    private volatile boolean produced = false;
    
    public void produce() throws InterruptedException {
        synchronized (lock) {
            while (produced) {
                lock.wait();
            }

            System.out.println(Thread.currentThread().getName() + ", p -->" + ++count);
            lock.notifyAll();
            produced = true;
        }
    }

    public void consumer() throws InterruptedException {
        synchronized (lock) {
            while (!produced) {
                lock.wait();
            }

            System.out.println(Thread.currentThread().getName() + ", c -->" + count);
            lock.notifyAll();
            produced = false;
        }
    }

    public static void main(String[] args) {
        ProcuceAndConsumerVersion3 pc = new ProcuceAndConsumerVersion3();

        Stream.of("P1", "P2", "P3").forEach(s -> new Thread(() -> {
            while (true) {
                try {
                    pc.produce();
                } catch (InterruptedException e) {
                    log.debug(e.getMessage(), e);
                }
            }
        }, s).start());

        Stream.of("C1", "C2", "C3", "C4").forEach(s -> new Thread(() -> {
            while (true) {
                try {
                    pc.consumer();
                } catch (InterruptedException e) {
                    log.debug(e.getMessage(), e);
                }
            }
        }, s).start());
    }
}
