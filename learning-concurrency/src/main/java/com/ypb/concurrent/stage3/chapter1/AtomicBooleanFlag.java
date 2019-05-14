package com.ypb.concurrent.stage3.chapter1;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AtomicBooleanFlag {

    //    private static final AtomicBoolean ab = new AtomicBoolean(true);
    private static final VolatileBoolean ab = new VolatileBoolean();

    private static boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
//        test1();

        test2();
    }

    private static void test2() throws InterruptedException {
        new Thread(AtomicBooleanFlag::runTask2).start();

        TimeUnit.MILLISECONDS.sleep(5000L);
        new Thread(()-> flag = false).start();
    }

    private static void runTask2() {
        while (flag) {

//            try {
//                TimeUnit.MILLISECONDS.sleep(1000);
                System.out.println("I am woking..");
//            } catch (InterruptedException e) {
//                log.debug(e.getMessage(), e);
//            }
        }

        System.out.println("I am finish..");
    }

    private static void test1() throws InterruptedException {
        new Thread(AtomicBooleanFlag::runTask).start();

        TimeUnit.MILLISECONDS.sleep(5000);

        ab.set(false);
    }

    private static void runTask() {
        while (ab.get()) {
            try {
                Thread.sleep(1000);
                System.out.println("I am working..");
            } catch (InterruptedException e) {
                log.debug(e.getMessage(), e);
            }
        }

        System.out.println("I am finish..");
    }

    private static class VolatileBoolean {
        private volatile boolean falg = true;

        public boolean get() {
            return falg;
        }

        public void set(boolean falg) {
            this.falg = falg;
        }
    }
}
