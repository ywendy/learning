package com.ypb.concurrent.stage3.chapter1;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class AtomicIntegerTest {

    private static volatile int value = 0;
    private static Set<Integer> set = Sets.newConcurrentHashSet();

    private static AtomicInteger ai = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        // 多线程对volatile变量操作写，有问题。会出现 Thread-0:1309和 Thread-2:1309
//        test1();
        test2();
    }

    private static void test2() throws InterruptedException {
        List<Thread> threads = Lists.newArrayList();
        IntStream.rangeClosed(1, 3).forEach(i -> threads.add(new Thread(AtomicIntegerTest::runTask2)));

        Thread t1 = threads.get(0);
        Thread t2 = threads.get(1);
        Thread t3 = threads.get(2);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("set.size() = " + set.size());
    }

    private static void test1() throws InterruptedException {
        List<Thread> threads = Lists.newArrayList();
        IntStream.rangeClosed(1, 3).forEach(i -> threads.add(new Thread(AtomicIntegerTest::runTask)));

        Thread t1 = threads.get(0);
        Thread t2 = threads.get(1);
        Thread t3 = threads.get(2);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("set.size() = " + set.size());
    }

    private static void runTask2() {
        int x = 0;
        while (x < 500) {
            int v = ai.getAndIncrement();
            set.add(v);

            System.out.println(Thread.currentThread().getName() + ":" + v);
            x++;
        }
    }

    private static void runTask() {
        int x = 0;
        while (x < 500) {
            set.add(value);
            int tmp = value;

            System.out.println(Thread.currentThread().getName() + ":" + tmp);

            value += 1;
            x++;
        }
    }
}
