package com.ypb.classloader;
/**
 * @className ClinitThreadTest
 * @description 验证jvm保证类只能被加载一次
 * @author yangpengbing
 * @date 22:35 2019/5/6
 * @version 1.0.0
 */
public class ClinitThreadTest {

    public static void main(String[] args) {
        new Thread(() -> new SimpleObject()).start();
        new Thread(() -> new SimpleObject()).start();
    }

    static class SimpleObject {

        static {
            System.out.println(Thread.currentThread().getName() + " I will be initial");
            while (Boolean.TRUE) {

            }

            System.out.println(Thread.currentThread().getName() + " I am finished initial.");
        }
    }
}
