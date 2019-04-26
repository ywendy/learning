package com.ypb.concurrent.stage1.chapter2;

import java.util.concurrent.TimeUnit;

/**
 * @className TicketWindowRunnable
 * @description 实现runnable接口，将线程和线程需要执行的任务分离
 * @author yangpengbing
 * @date 23:56 2019/4/4
 * @version 1.0.0
 */
public class TicketWindowRunnable implements Runnable {

    private static final int MAX = 50;
    private int index = 1;

    @Override
    public void run() {
        while (index <= MAX) {
            System.out.println(Thread.currentThread() + "当前的号码是：" + (index++));

            try {
                TimeUnit.MILLISECONDS.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
