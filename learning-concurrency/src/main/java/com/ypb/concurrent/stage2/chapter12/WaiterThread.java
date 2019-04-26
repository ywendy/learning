package com.ypb.concurrent.stage2.chapter12;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WaiterThread extends Thread {

    private final BalkingData data;

    public WaiterThread(BalkingData data) {
        super("Waiter");
        this.data = data;
    }

    @Override
    public void run() {
        for (int i = 0; i < 200; i++) {
            try {
                data.save();

                TimeUnit.MILLISECONDS.sleep(1_000);
            } catch (Exception e) {
                log.debug(e.getMessage(), e);
            }
        }
    }
}
