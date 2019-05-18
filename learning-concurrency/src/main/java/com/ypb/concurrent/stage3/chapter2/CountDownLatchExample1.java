package com.ypb.concurrent.stage3.chapter2;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * @className CountDownLatchExample1
 * @description 使用CountDownLatch分配处理数组，如果数组的值是偶数，值乘以2，如果是奇数乘以10
 * @author yangpengbing
 * @date 22:24 2019/5/18
 * @version 1.0.0
 */
@Slf4j
public class CountDownLatchExample1 {

    private static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) throws InterruptedException {
        int size = 10;
        // 1
        int[] arrs = query(size);
        // 2
        CountDownLatch latch = new CountDownLatch(size);
        ExecutorService pool = Executors.newFixedThreadPool(2);
        for (int i = 0; i < arrs.length; i++) {
            pool.execute(new SimpleRunnable(arrs, i, latch));
        }

        // 3
        latch.await();
        System.out.println("all of work finish done.");
        pool.shutdown();

//        pool.execute(() -> System.out.println("I am game over"));
    }

    private static int[] query(int size) {
        int[] arrs = new int[size];
        for (int i = 0; i < size; i++) {
            arrs[i] = i;
        }

        return arrs;
    }

    private static final class SimpleRunnable implements Runnable {

        private final int[] arrs;
        private final int index;
        private CountDownLatch latch;

        public SimpleRunnable(int[] arrs, int index, CountDownLatch latch) {
            this.arrs = arrs;
            this.index = index;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                slowly(2000);
            } catch (InterruptedException e) {
                log.debug(e.getMessage(), e);
            }

            int value = arrs[index];
            if (value % 2 == 0) {
                value = value * 2;
            } else {
                value = value * 10;
            }

            arrs[index] = value;

            System.out.println(Thread.currentThread().getName() + ", index [" + index + "] finish.");
            latch.countDown();
        }

        private void slowly(int mills) throws InterruptedException {
            TimeUnit.MILLISECONDS.sleep(random.nextInt(mills));
        }
    }
}
