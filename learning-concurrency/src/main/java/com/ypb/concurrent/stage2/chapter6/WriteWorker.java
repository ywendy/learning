package com.ypb.concurrent.stage2.chapter6;

import java.util.Random;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WriteWorker extends Thread {

    private final SharedDate date;
    private final String filter;
    private int index;

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    public WriteWorker(SharedDate date, String filter) {
        this.date = date;
        this.filter = filter;
    }

    @Override
    public void run() {
        try {
            while (true) {
                char c = nextChar();
                date.write(c);
                Thread.sleep(RANDOM.nextInt(1000));
            }
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }
    }

    private char nextChar() {
        char c = filter.charAt(index);
        index++;

        if (index >= filter.length()) {
            index = 0;
        }

        return c;
    }
}
