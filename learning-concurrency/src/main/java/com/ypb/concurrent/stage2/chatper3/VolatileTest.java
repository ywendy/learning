package com.ypb.concurrent.stage2.chatper3;

import lombok.extern.slf4j.Slf4j;

/**
 * @className VolatileTest
 * @description volatile测试类，验证volatile修饰的变量，对其他线程的可见性，禁止JVM对其进行指令的重排序
 * @author yangpengbing
 * @date 22:49 2019/4/19
 * @version 1.0.0
 */
@Slf4j
public class VolatileTest {

    private static volatile int intValue = 0;
    private static final int MAX_VALUE = 50;

    // 定义两个线程，一个线程读，一个行程写
    public static void main(String[] args) {
        new Thread(() -> {
            int localValue = intValue;
            while (localValue < MAX_VALUE) {
                if (localValue != intValue) {
                    System.out.printf("the value updated to [%d]\n", intValue);
                    localValue = intValue;
                }
            }

        }, "READER").start();

        new Thread(() -> {
            int localValue = intValue;
            while (localValue < MAX_VALUE) {
                System.out.printf("update the value to [%d]\n", ++localValue);
                intValue = localValue;

                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    log.debug(e.getMessage(), e);
                }
            }

        }, "UPDATER").start();
    }

    /**************************** 学习笔记(2019年4月19日) ******************************/
//    reader线程会死循环，updater线程会每隔1s打印、
//    如果一个线程内对共享变量全都是读操作，那么这个共享变量的值只会从主内存中获取一次，以后不会在从主内存中获利，但这个前提是共享变量没有加volatile关键


}
