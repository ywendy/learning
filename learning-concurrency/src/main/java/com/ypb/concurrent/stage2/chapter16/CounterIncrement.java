package com.ypb.concurrent.stage2.chapter16;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CounterIncrement extends Thread {

    private volatile boolean terminated = false;
    private int counter;
    private static final Random random = new Random(System.currentTimeMillis());

    @Override
    public void run() {
        try {
            while (!terminated) {
                printConsole();

                slowly(1000);
            }
        } catch (InterruptedException e) {
            log.debug(e.getMessage(), e);
        } finally {
            clean();
        }
    }

    private void clean() {
        System.out.println("do same clean work for the second phase, curent counter " + counter);
    }

    public void slowly(int mills) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(mills);
    }

    private void printConsole() {
        System.out.println(Thread.currentThread().getName() + " " + counter++);
    }

    public void close() {
        this.terminated = true;
        this.interrupt();
    }
}
