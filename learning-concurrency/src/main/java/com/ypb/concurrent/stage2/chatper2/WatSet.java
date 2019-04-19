package com.ypb.concurrent.stage2.chatper2;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yangpengbing
 * @version 1.0.0
 * @className WatSet
 * @description waitset队列
 * @date 22:43 2019/4/19
 */
@Slf4j
public class WatSet {

    public static final Object LOCK = new Object();

    private static void work() {
        synchronized (LOCK) {
            System.out.println("begin...");
            try {
                System.out.println("thread all coming..");
                LOCK.wait();
            } catch (InterruptedException e) {
                log.debug(e.getMessage(), e);
            }
            System.out.println("thread all complete...");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->work()).start();

        Thread.sleep(1_000);
        synchronized (LOCK) {
            LOCK.notifyAll();
        }
    }
}
