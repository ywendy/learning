package com.ypb.concurrent.stage2.chatper3;

import lombok.extern.slf4j.Slf4j;

/**
 * @className VolatileTest2
 * @description 验证volatile修饰的变量，不保证原子性
 * @author yangpengbing
 * @date 23:08 2019/4/19
 * @version 1.0.0
 */
@Slf4j
public class VolatileTest2 {

    private static int intValue = 0;
    private static final int MAX_VALUE = 50;

    public static void main(String[] args) {
        new Thread(() -> worker(), "ADDER_1").start();

        new Thread(() -> worker(), "ADDER_2").start();
    }

    private static void worker() {
        while (intValue < MAX_VALUE) {
            int localValue = intValue;
            localValue++;
            intValue = localValue;

            System.out.println(Thread.currentThread().getName() + " ->" + localValue);

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                log.debug(e.getMessage(), e);
            }
        }
    }

    /**************************** 学习笔记(2019年4月19日) ******************************/
//    并发编程的三个要素
//    1. 原子性,
//    2. 可见性
//    3. 有序性

//    volatile保证了可见性和有序性， 但是不保证原子性

//    实现原型是：volatile修饰的变量，变量进行写操作后，立即刷新到主内存，并通知其他线程这个变量的值已经发生变化，线程本地变量副本缓存失效
//    volatile修饰的变量，在进行读操作之前，所有的写操作必须完成。appends-before规则，禁止了JVM对其进行重排序

//    屏障，不允许屏障之前的指令放在屏障之后，

//    happens-before规则：
//    1. 代码执行规则：编写的前面的代码发生在编写后面的， 也就是，只有先声明了变量，才能使用，不能先使用变量，后声明
//    2. unlock必须在lock后面
//    3. volatile修饰的变量，写操作优先于读操作

//    重排序只需要保证最终一致性即可，
}
