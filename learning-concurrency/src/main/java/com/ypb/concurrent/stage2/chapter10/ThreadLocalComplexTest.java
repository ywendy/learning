package com.ypb.concurrent.stage2.chapter10;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalComplexTest {

    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<String>() {

        @Override
        protected String initialValue() {
            return "main value";
        }
    };

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            THREAD_LOCAL.set("Thread-T1");

            try {
                slowly(RANDOM.nextInt(1000));
            } catch (InterruptedException e) {
                log.debug(e.getMessage(), e);
            }

            print();
        });

        Thread t2 = new Thread(() -> {
            THREAD_LOCAL.set("Thread-T2");
            try {
                slowly(RANDOM.nextInt(500));
            } catch (InterruptedException e) {
                log.debug(e.getMessage(), e);
            }

            print();
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        print();
    }

    private static void print() {
        System.out.println(Thread.currentThread().getName() + " " + THREAD_LOCAL.get());
    }

    private static void slowly(int mills) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(mills);
    }
}
