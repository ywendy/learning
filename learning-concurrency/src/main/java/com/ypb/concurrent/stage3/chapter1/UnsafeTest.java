package com.ypb.concurrent.stage3.chapter1;

import com.google.common.base.Stopwatch;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UnsafeTest {

    public static void main(String[] args) throws Exception {
//        test1();
//        Unsafe unsafe = test2();
//        System.out.println("unsafe = " + unsafe);

        test3();
    }

    /**
     * StupidCounter
     * stopwatch.toString() = 554.8 ms
     * counter.getCounter() = 9947819
     * -----------------------------
     * SynchronizedCounter
     * stopwatch.toString() = 629.2 ms
     * counter.getCounter() = 10000000
     * -----------------------------
     * LockCounter
     * stopwatch.toString() = 496.1 ms
     * counter.getCounter() = 10000000
     * -----------------------------
     * AtomicLongCounter
     * stopwatch.toString() = 714.2 ms
     * counter.getCounter() = 10000000
     * -----------------------------
     * CASCounter
     * stopwatch.toString() = 780.3 ms
     * counter.getCounter() = 10000000
     * -----------------------------
     * @throws InterruptedException
     */
    private static void test3() throws Exception {
        int nThreads = 1000;
        ExecutorService pool = Executors.newFixedThreadPool(nThreads);
        Counter counter = new CASCounter();

        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < nThreads; i++) {
            pool.submit(new CounterRunnable(counter, nThreads * 10));
        }

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.HOURS);

        stopwatch.stop();
        System.out.println("stopwatch.toString() = " + stopwatch.toString());
        System.out.println("counter.getCounter() = " + counter.getCounter());
    }

    private interface Counter {

        void increment();

        long getCounter();
    }

    private static class StupidCounter implements Counter {

        private long counter;

        @Override
        public void increment() {
            counter++;
        }

        @Override
        public long getCounter() {
            return counter;
        }
    }

    private static class SynchronizedCounter implements Counter {

        private long counter;

        @Override
        public synchronized void increment() {
            counter++;
        }

        @Override
        public long getCounter() {
            return counter;
        }
    }

    private static class LockCounter implements Counter {

        private long counter;
        private final Lock lock = new ReentrantLock();

        @Override
        public void increment() {
            try {
                lock.lock();
                counter++;
            }finally {
                lock.unlock();
            }
        }

        @Override
        public long getCounter() {
            return counter;
        }
    }

    private static class AtomicLongCounter implements Counter {
        private AtomicLong counter = new AtomicLong();

        @Override
        public void increment() {
            counter.getAndIncrement();
        }

        @Override
        public long getCounter() {
            return counter.get();
        }
    }

    private static class CASCounter implements Counter {
        private volatile long counter;
        private final Unsafe unsafe;
        private long offset;

        private CASCounter() throws Exception {
            unsafe = test2();
            offset = unsafe.objectFieldOffset(CASCounter.class.getDeclaredField("counter"));
        }

        @Override
        public void increment() {
            long current = counter;
            while (!unsafe.compareAndSwapLong(this, offset, current, current + 1)) {
                current = counter;
            }
        }

        @Override
        public long getCounter() {
            return counter;
        }
    }

    private static class CounterRunnable implements Runnable {

        private final Counter counter;
        private final int num;

        private CounterRunnable(Counter counter, int num) {
            this.counter = counter;
            this.num = num;
        }

        @Override
        public void run() {
            for (int i = 0; i < num; i++) {
                counter.increment();
            }
        }
    }

    private static Unsafe test2() throws NoSuchFieldException, IllegalAccessException {
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);

        return (Unsafe) field.get(null);
    }

    /**
     * java.lang.SecurityException: Unsafe
     */
    private static void test1() {
        Unsafe unsafe = Unsafe.getUnsafe();
    }
}
