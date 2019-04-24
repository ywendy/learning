package com.ypb.concurrent.stage2.chapter10;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalSimulatorTest {

    public static final ThreadLocalSimulator<String> SIMULATOR = new ThreadLocalSimulator<String>(){
        @Override
        protected String initialValue() {
            return "no value";
        }
    };

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(ThreadLocalSimulatorTest::initRunnable);
        Thread t2 = new Thread(ThreadLocalSimulatorTest::initRunnable);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        print();
    }

    private static void initRunnable() {
        SIMULATOR.set(Thread.currentThread().getName());

        try {
            slowly(RANDOM.nextInt(1000));
        } catch (InterruptedException e) {
            log.debug(e.getMessage(), e);
        }

        print();
    }

    private static void print() {
        System.out.println(Thread.currentThread().getName() + " set value -> " + SIMULATOR.get());
    }

    private static void slowly(int mills) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(mills);

    }

}
