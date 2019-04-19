package com.ypb.concurrent.stage1.chapter1;
/**
 * @className ThreadTest
 * @description 同时继承Thread类和实现Runnable接口的线程，执行的run方法是哪个?
 * @author yangpengbing
 * @date 22:14 2019/4/10
 * @version 1.0.0
 */
public class ThreadTest {

    public static void main(String[] args) {
        new Thread(() -> System.out.println("I running of the implements Runnable run method.")) {

            @Override
            public void run() {
                System.out.println("I running of the extends Thread run method.");
            }
        }.start();

    }
}
