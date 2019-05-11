package com.ypb.concurrent.stage1.chapter9;

import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yangpengbing
 * @version 1.0.0
 * @className DifferenceOfWaitAndSleep
 * @description sleep和wait的区别
 * @date 20:39 2019/5/11
 */
@Slf4j
public class DifferenceOfWaitAndSleep {

    private static final Object lock = new Object();

    public static void main(String[] args) {
        Stream.of("S1", "S2").forEach(s -> new Thread(DifferenceOfWaitAndSleep::sleepMethod, s).start());
        Stream.of("C1", "C2").forEach(s -> new Thread(DifferenceOfWaitAndSleep::waitMethod, s).start());
    }

    public static void sleepMethod() {
        synchronized (lock) {
            System.out.println(Thread.currentThread().getName() + " start of sleep method.");
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                log.debug(e.getMessage(), e);
            }
        }
    }

    public static void waitMethod() {
        synchronized (lock) {
            System.out.println(Thread.currentThread().getName() + " start of wait method.");
            try {
                lock.wait(10_000);
            } catch (InterruptedException e) {
                log.debug(e.getMessage(), e);
            }
        }
    }

    /**************************** 学习笔记(2019年5月11日) ******************************/
//    sleep和wait方法的区别：
//    sleep是Thread类的方法，wait是Object类的方法
//    sleep不会释放object monitor，而wait是会释放object monitor的，而且线程会保存到wait set队列中
//    sleep不会有被唤醒，而wait方法是需要被唤醒的，唤醒需要使用while方法，不能使用if
//    sleep不依赖object monitor，而wait方法依赖
}
