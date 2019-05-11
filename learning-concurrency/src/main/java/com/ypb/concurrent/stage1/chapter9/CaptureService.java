package com.ypb.concurrent.stage1.chapter9;

import com.google.common.collect.Lists;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

/**
 * @className CaptureService
 * @description 生产者和消费者模式应用，模拟爬虫服务
 * @author yangpengbing
 * @date 21:10 2019/5/11
 * @version 1.0.0
 */
@Slf4j
public class CaptureService {

    private static final List<Thread> workers = Lists.newArrayList();
    private static final LinkedList<Thread> count = Lists.newLinkedList();
    private static final int max = 5;
    private static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        IntStream.rangeClosed(1, 20).mapToObj(i -> "M" + i).map(CaptureService::createCaptrueThred).forEach(Thread::start);

        Consumer<? super Thread> consumer = thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                log.debug(e.getMessage(), e);
            }
        };
        workers.forEach(consumer);

        printConsole(Optional.of("All of capture worker finish."));
    }
    
    private static Thread createCaptrueThred(String name) {
        Thread thread = new Thread(CaptureService::captureWorker, name);
        workers.add(thread);

        return thread;
    }

    private static void captureWorker() {
        printConsole(Optional.of("the worker [" + Thread.currentThread().getName() + "] begin capture data"));

        synchronized (count) {
            while (count.size() > max) {
                try {
                    count.wait();
                } catch (InterruptedException e) {
                    log.debug(e.getMessage(), e);
                }
            }

            count.addLast(Thread.currentThread());
        }

        captureWorking();

        synchronized (count) {
            count.removeFirst();
            count.notifyAll();
        }
    }

    private static void captureWorking() {
        Optional<String> opt = Optional.of("the worker [" + Thread.currentThread().getName() + "] is working start..");
        printConsole(opt);

        try {
            slowly(10000);
        } catch (InterruptedException e) {
            log.debug(e.getMessage(), e);
        }

        opt = Optional.of("the worker [" + Thread.currentThread().getName() + "] is worker finsish..");
        printConsole(opt);
    }

    private static void slowly(int millis) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(random.nextInt(millis));
    }

    private static void printConsole(Optional<String> opt) {
        opt.ifPresent(System.out::println);
    }
}
