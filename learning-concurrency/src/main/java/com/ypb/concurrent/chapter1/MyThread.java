package com.ypb.concurrent.chapter1;
/**
 * @className MyThread
 * @description 继承Thread类的方式，创建线程
 * @author yangpengbing
 * @date 20:02 2019/4/7
 * @version 1.0.0
 */
public class MyThread extends Thread {

    private final String name;

    public MyThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        int times = 5;
        for (int i = 0; i < times; i++) {
            System.out.println(name + "运行：" + i);
            try {
                sleep((long) (Math.random() * 10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new MyThread("A");
        Thread t2 = new MyThread("B");

        t1.start();
        t2.start();
        t2.start();
    }
}
