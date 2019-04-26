package com.ypb.concurrent.stage1.chapter3;

/**
 * @className CreateThread4
 * @description 创建THread构造方法中的stackSize
 * @author yangpengbing
 * @date 21:53 2019/4/8
 * @version 1.0.0
 */
public class CreateThread4 {

    private static int counter = 0;

    public static void main(String[] args) {

        Thread t1 = new Thread(null, () -> add(1), "Test-Thread", 1 << 24);

        t1.start();

        Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("thread: " + t + "e：" + e);
                System.out.println("counter = " + counter);
            }
        };
        t1.setUncaughtExceptionHandler(handler);

        // counter = 680237
    }

    private static void add(int i) {
        ++counter;
        add(i + 1);
    }

    /**
     * 计算1左移24位是多少m
     */
    public void calcSize() {
        // 1<<24是2的24次方，除以8得到是字节数，在除以1024是k，在除以1024是m，在除以1024是g
        // 1<<32等到的结果是1
        // 24-3=21是字节数，21-10=11是k，11-10=1是m，也就是2的一次方等于2m
        // 30-3=27是字节数，27-10=17是k，17-10=7是m，也就是2的7次方等于128m
        System.out.println(1 << 24);
    }
}
