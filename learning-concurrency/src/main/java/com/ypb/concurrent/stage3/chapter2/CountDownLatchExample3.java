package com.ypb.concurrent.stage3.chapter2;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CountDownLatchExample3 {

    public static void main(String[] args) throws InterruptedException {
//        test1();
        test2();
    }

    private static void test2() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Thread mainThread = Thread.currentThread();

        new Thread(()->{
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                log.debug(e.getMessage(), e);
            }

//            latch.countDown();
            mainThread.interrupt();

        }).start();

        latch.await(100, TimeUnit.MILLISECONDS);  // 这个是主线程被wait住了
        System.out.println("================");
        latch.countDown();
    }

    private static void test1() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(0);

        latch.await();

        System.out.println("test count equals zero await");
    }
}
