package com.ypb.concurrent.stage2.chapter16;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CounterTest {

    public static void main(String[] args) {
        CounterIncrement increment = new CounterIncrement();

        increment.start();

        try {
            increment.slowly(15_000);
        } catch (InterruptedException e) {
            log.debug(e.getMessage(), e);
        }

        increment.close();
    }
}
