package com.ypb.concurrent.stage2.chapter13;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yangpengbing
 * @version 1.0.0
 * @className CustomerThread
 * @description 消费者线程
 * @date 21:55 2019/4/26
 */
@Slf4j
public class CustomerThread extends Thread {

    private final MessageQueue queue;
    private static final Random random = new Random(System.currentTimeMillis());

    public CustomerThread(MessageQueue queue, int seq) {
        super("customer-" + seq);
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message take = queue.take();
                printConsole(take.getMessage());

                slowly(1000);
            } catch (InterruptedException e) {
                log.debug(e.getMessage(), e);
                break;
            }
        }
    }

    private void slowly(int mills) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(random.nextInt(mills));
    }

    private void printConsole(String message) {
        System.out.println(Thread.currentThread().getName() + " take a message " + message);
    }
}
