package com.ypb.concurrent.chapter3;
/**
 * @className CreateThread
 * @description 创建线程
 * @author yangpengbing
 * @date 15:17 2019/4/5
 * @version 1.0.0
 */
public class CreateThread {

    public static void main(String[] args) {
        Thread t1 = new Thread();
        Thread t2 = new Thread(){

            @Override
            public void run() {
                System.out.println("==========");
            }
        };

        t1.start();
        t2.start();
        System.out.println(t1.getName());
        System.out.println(t2.getName());

        Thread t3 = new Thread("MyName");
        Thread t4 = new Thread(() -> System.out.println("Runnable..."));

        System.out.println(t3.getName());
        System.out.println(t4.getName());

        Thread t5 = new Thread(() -> System.out.println("Runnable...." + Thread.currentThread().getName()), "RunnableThread");
        t5.start();
    }
}
