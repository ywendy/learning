package com.ypb.concurrent.stage2.chapter10;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yangpengbing
 * @version 1.0.0
 * @className ThreadLockSimpleTest
 * @description ThreadLock的简单测试
 * @date 22:02 2019/4/24
 */
@Slf4j
public class ThreadLockSimpleTest {

    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return "hello yangpengbing";
        }
    };

    public static void main(String[] args) {
        String value = "yangpengbing";
//        THREAD_LOCAL.set(value);

        print("===============");
        try {
            slowly(500);
        } catch (InterruptedException e) {
            log.debug(e.getMessage(), e);
        }
        print(THREAD_LOCAL.get());
    }

    private static void slowly(int mills) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(mills);
    }

    private static void print(String msg) {
        System.out.println(msg);
    }
}
