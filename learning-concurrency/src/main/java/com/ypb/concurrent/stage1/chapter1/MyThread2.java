package com.ypb.concurrent.stage1.chapter1;
/**
 * @className MyThread2
 * @description 实现Runnable接口，创建线程
 * @author yangpengbing
 * @date 20:30 2019/4/7
 * @version 1.0.0
 */
public class MyThread2 {

    public static void main(String[] args) {
        new Thread(new MyRunnable("A")).start();
        new Thread(new MyRunnable("B")).start();
    }
}

class MyRunnable implements Runnable {

    private final String name;

    public MyRunnable(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(name + "运行：" + i);

            try {
                Thread.sleep((long) (Math.random() * 10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

