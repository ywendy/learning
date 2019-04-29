package com.ypb.concurrent.stage2.chapter18;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MarkingStringClientThread extends Thread {

    private final ActiveObject activeObject;
    private static final Random random = new Random(System.currentTimeMillis());

    private static final String strs;
    static {
        strs = "1234567890qwertyuiopasdfghjklzxcvbnm";
    }

    public MarkingStringClientThread(String name, ActiveObject activeObject) {
        super(name);

        this.activeObject = activeObject;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; true; i++) {
                char fillChar = initFillChar(i);

                String result = markingString(i + 1, fillChar).getResultValue();

                slowly(10);

                System.out.println(Thread.currentThread().getName() + ": value = " + result);
            }
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }
    }

    private char initFillChar(int index) {
        if (index <= 0) {
            index = 1;
        }
        index = random.nextInt(index) % strs.length();
        return strs.charAt(index);
    }

    private void slowly(int mills) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(mills);
    }

    private Result<String> markingString(int count, char fillChar) {
        return activeObject.makeString(count, fillChar);
    }
}


