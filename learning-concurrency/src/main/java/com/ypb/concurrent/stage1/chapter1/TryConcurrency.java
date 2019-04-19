package com.ypb.concurrent.stage1.chapter1;

import lombok.extern.slf4j.Slf4j;

/**
 * @className TryConcurrency
 * @description 尝试java的Thread
 * @author yangpengbing
 * @date 14:47 2019/4/5
 * @version 1.0.0
 */
@Slf4j
public class TryConcurrency {

    public static void main(String[] args) {
        Thread readThread = new Thread("READ-thread") {

            @Override
            public void run() {
                println(Thread.currentThread().getName());
            }
        };

        Thread writeThread = new Thread("WRITE-thread") {

            @Override
            public void run() {
                println(Thread.currentThread().getName());
            }
        };

        // 如果没有调用线程的start()的话，不会信创建一个线程，输出的是主线程的名称main
        // 每个线程在创建后调用start()的时候，至少有两个线程，一个是被创建的线程，一个是被调用的线程。
        // main主线程是JVM创建的，
        readThread.start();
        writeThread.start();
    }

    /**
     * 模拟从DB中读取数据
     */
    private static void readFromDataBase() {
        // read data from database and handle it.
        try {
            println("Begin read from db.");
            Thread.sleep(1000 * 30L);
            println("Read data and start handle it.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        println("The data handle finish and successfully");
    }

    private static void writeDataToFile() {
        try {
            println("Begin write data to file.");
            Thread.sleep(1000 * 20L);
            println("Write data done and start handle it.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        println("The data handle finish and successfully.");
    }

    private static void println(String s) {
        log.info(s);
    }
}
