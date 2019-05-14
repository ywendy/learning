package com.ypb.concurrent.stage2.chapter3;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VolatileAndSynchronizedTest {

    public static void main(String[] args) throws InterruptedException {
        test1();
//        test2();
//        test3();
    }

    private static void test3() {
        VolatileTest test = new VolatileTest();
        for (int i = 0; i < 8; i++) {
            new Thread(() -> {
                int x = 0;
                while (true) {
                    test.setResult(x++);

                    System.out.println("x = " + x);
                }

            }).start();
        }

        while (true) {
            System.out.println("test.getResult() = " + test.getResult());
        }
    }

    private static void test2() throws InterruptedException {
        VolatileTest test = new VolatileTest();
        for (int i = 0; i < 8; i++) {
            new Thread(() -> {
                int x = 0;
                while (test.getResult() < 100) {
                    x++;
                }

                System.out.println("x = " + x);
            }).start();
        }
        TimeUnit.MILLISECONDS.sleep(1000);
        test.setResult(200);
    }

    private static void test1() throws InterruptedException {
        SynchronizedTest test = new SynchronizedTest();
        for (int i = 0; i < 8; i++) {
            new Thread(() -> {
                int x = 0;
                while (test.getResult() < 100) {
                    x++;
                }

                System.out.println("x = " + x);
            }).start();

        }

        Thread.sleep(1000);
        test.setResult(200);
    }

    private static class SynchronizedTest {
        private int result;

        public int getResult() {
            return result;
        }

        public synchronized void setResult(int result) {
            this.result = result;
        }
    }

    private static class VolatileTest {
        private volatile int result;

        public synchronized int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }
    }
}
