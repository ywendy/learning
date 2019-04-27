package com.ypb.concurrent.stage2.chapter16;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppServerClient {

    public static void main(String[] args) {
        AppServer as = new AppServer(12345);
        as.start();

        try {
            slowly(15_000);
            as.shutdown();
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }
    }

    private static void slowly(int mills) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(mills);
    }
}
