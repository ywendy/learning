package com.ypb.concurrent.chapter3;

import java.util.Arrays;

public class CreateThread2 {

    public static void main(String[] args) {
        Thread t = new Thread(){

            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        t.start();
        System.out.println(t.getThreadGroup());
        // main
//        System.out.println(Thread.currentThread().getName());

        ThreadGroup tg = Thread.currentThread().getThreadGroup();
        System.out.println(tg.getName());

        System.out.println(tg.activeCount());

        Thread[] threads = new Thread[tg.activeCount()];
        tg.enumerate(threads);

        Arrays.asList(threads).forEach(System.out::println);
    }
}
