package com.ypb.concurrent.stage3.chapter1;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AtomicIntegerDetailsTest2 {

    private static final CompareAndSetLock lock = new CompareAndSetLock();

    public static void main(String[] args) {
        int times = 5;
//        test1(times);
        test2(times);
    }

    private static void test2(int times) {
        for (int i = 0; i < times; i++) {
            new Thread(()->{
                try {
                    doSomething2();
                } catch (CompareAndSetLock.GetLockException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        lock.unLock();
    }

    private static void test1(int times) {
        for (int i = 0; i < times; i++) {
            new Thread(()->{
                try {
                    doSomething();
                } catch (InterruptedException e) {
                    log.debug(e.getMessage(), e);
                }
            }).start();
        }
    }

    /**
     * synchronized关键字，锁没有释放，后面想线程不执行
     * @throws InterruptedException
     */
    private static void doSomething() throws InterruptedException {
        synchronized (AtomicIntegerDetailsTest2.class) {
            System.out.println(Thread.currentThread().getName() + " get the lock of doSomething..");

            Thread.sleep(100_000);
        }
    }
    
    private static void doSomething2() throws CompareAndSetLock.GetLockException, InterruptedException {
        try {
            lock.tryLock();
            System.out.println(Thread.currentThread().getName() + " get the lock of doSomething2..");

            Thread.sleep(100_000);
        }finally {
            lock.unLock();
        }
    }

}
