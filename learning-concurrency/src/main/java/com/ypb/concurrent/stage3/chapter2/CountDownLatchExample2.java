package com.ypb.concurrent.stage3.chapter2;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CountDownLatchExample2 {

    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(1);

        new Thread(()->{
            printLog("do some initial working..");

            try {
                latch.await();
            } catch (InterruptedException e) {
                log.debug(e.getMessage(), e);
            }

            printLog("do other working..");
        }).start();

        new Thread(()->{
            try {
                latch.await();
                printLog("test await...");
            } catch (InterruptedException e) {
                log.debug(e.getMessage(), e);
            }
        }).start();

        new Thread(()->{
            printLog("async prepare for some data..");
            try {
                TimeUnit.MILLISECONDS.sleep(2000);
                printLog("data prepare for done..");
            } catch (InterruptedException e) {
                log.debug(e.getMessage(), e);
            }finally {
                latch.countDown();
            }
        }).start();
    }

    private static void printLog(String msg) {
        System.out.println(Thread.currentThread().getName() + " --> " + msg);
    }
}
