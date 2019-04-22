package com.ypb.concurrent.stage2.chapter6;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReadWorker extends Thread {

    private final SharedDate date;

    public ReadWorker(SharedDate date) {
        this.date = date;
    }

    @Override
    public void run() {
        try {
            while (true) {
                char[] read = date.read();
                print(read);
            }
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }
    }

    private void print(char[] read) {
        System.out.println(Thread.currentThread().getName() + " reads " + String.valueOf(read));
    }
}
