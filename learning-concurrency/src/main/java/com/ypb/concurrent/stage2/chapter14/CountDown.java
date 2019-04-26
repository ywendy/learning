package com.ypb.concurrent.stage2.chapter14;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yangpengbing
 * @version 1.0.0
 * @className CountDown
 * @description countDown pattern
 * @date 22:22 2019/4/26
 */
@Slf4j
public class CountDown {

    private int total;
    private int counter;

    public CountDown(int down) {
        this.total = down;
    }

    public void down() {
        synchronized (this) {
            counter++;
            this.notifyAll();
        }
    }

    public void await() throws InterruptedException {
        synchronized (this) {
            while (counter != total) {
                this.wait();
            }
        }
    }
}
