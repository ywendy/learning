package com.ypb.concurrent.stage1.chapter10;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BooleanLockTest {

    private static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) throws InterruptedException {
        final Lock lock = new BooleanLock();

        IntStream.rangeClosed(1, 3).mapToObj(i -> "T" + i).forEach(s -> new Thread(()->{
            try {
                lock.lock(random.nextInt(1000));

                Optional.of(Thread.currentThread().getName() + " have the lock monitor..").ifPresent(System.out::println);

                worker();
            } catch (InterruptedException e) {
                log.debug(e.getMessage(), e);
            } catch (Lock.TimeOutException e) {
                log.debug(e.getMessage(), e);
            } finally {
                lock.unLock();
            }
        }, s).start());

        TimeUnit.MILLISECONDS.sleep(100);
        lock.unLock();
    }

    private static void worker() throws InterruptedException {
        Optional.of(Thread.currentThread().getName() + " is working..").ifPresent(System.out::println);
        TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
    }
}
