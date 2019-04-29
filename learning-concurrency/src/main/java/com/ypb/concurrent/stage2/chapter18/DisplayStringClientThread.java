package com.ypb.concurrent.stage2.chapter18;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DisplayStringClientThread extends Thread {

    private final ActiveObject activeObject;

    private static final Random random = new Random(System.currentTimeMillis());

    public DisplayStringClientThread(String name, ActiveObject activeObject) {
        super(name);
        this.activeObject = activeObject;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; true; i++) {
                String text = Thread.currentThread().getName() + "-->" + i;

                activeObject.displayString(text);

                slowly(5);
            }
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }
    }

    private void slowly(int mills) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(mills);
    }
}
