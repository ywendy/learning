package com.ypb.concurrent.stage2.chapter15;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageHandler {

    private static final Random random = new Random(System.currentTimeMillis());
    private static final ExecutorService pool = Executors.newFixedThreadPool(5);

    public void request(Message message) {
        pool.submit(() -> initRunnable(message));

//        new Thread(() -> initRunnable(message)).start();
    }

    private void initRunnable(Message message) {
        String value = message.getValue();
        value = "the message will handler by " + Thread.currentThread().getName() + " " + value;
        System.out.println(value);

        try {
            slowly(1000);
        } catch (InterruptedException e) {
            log.debug(e.getMessage(), e);
        }
    }

    private void slowly(int mills) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(random.nextInt(mills));
    }

    public void shutdown() {
        pool.shutdown();
    }
}
