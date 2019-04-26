package com.ypb.concurrent.stage2.chapter13;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;
/**
 * @className ProducerThread
 * @description 生产者线程
 * @author yangpengbing
 * @date 21:55 2019/4/26
 * @version 1.0.0
 */
@Slf4j
public class ProducerThread extends Thread {

    private final MessageQueue queue;
    private static final AtomicInteger ai = new AtomicInteger();
    private static final Random random = new Random(System.currentTimeMillis());

    public ProducerThread(MessageQueue queue, int seq) {
        super("producer-" + seq);
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message message = initMessage();
                queue.put(message);

                printConsole(message.getMessage());

                slowly(1000);
            } catch (Exception e) {
                log.debug(e.getMessage(), e);
                break;
            }
        }
    }

    private void printConsole(String message) {
        System.out.println(Thread.currentThread().getName() + " put a message " + message);
    }

    private void slowly(int mills) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(random.nextInt(mills));
    }

    private Message initMessage() {
        String message = "message-" + ai.getAndIncrement();

        return new Message(message);
    }
}
