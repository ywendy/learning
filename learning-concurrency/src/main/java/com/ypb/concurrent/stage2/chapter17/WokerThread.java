package com.ypb.concurrent.stage2.chapter17;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WokerThread extends Thread {

    private final Channel channel;
    private volatile boolean stop = false;
    private static final Random random = new Random(System.currentTimeMillis());

    public WokerThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }

    @Override
    public void run() {
        while (!stop) {
            channel.take().execute();

            try {
                slowly(1_000);
            } catch (InterruptedException e) {
                log.debug(e.getMessage(), e);
            }
        }
    }
    
    public void stopWorker() {
        this.stop = true;
        this.interrupt();
    }

    private void slowly(int mills) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(random.nextInt(mills));
    }
}

