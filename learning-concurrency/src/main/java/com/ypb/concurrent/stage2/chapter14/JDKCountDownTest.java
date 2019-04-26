package com.ypb.concurrent.stage2.chapter14;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JDKCountDownTest {

    private static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) throws InterruptedException {
        printConsole("multi thread starting step one work..");

        CountDownLatch latch = new CountDownLatch(5);
        IntStream.rangeClosed(1, 5).forEach(i -> new Thread(String.valueOf(i)) {
            @Override
            public void run() {
                String msg = Thread.currentThread().getName() + " start working..";

                printConsole(msg);

                try {
                    slowly(2002);
                    latch.countDown();
                } catch (InterruptedException e) {
                    log.debug(e.getMessage(), e);
                }
            }
        }.start());

        latch.await();

        printConsole("step one work finish and staring step two work..");
        slowly(1000);
        printConsole("step two work finish..");
    }

    private static void slowly(int mills) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(random.nextInt(mills));
    }

    private static void printConsole(String msg) {
        System.out.println(msg);
    }
}
