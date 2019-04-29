package com.ypb.concurrent.stage2.chapter18;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Servant implements ActiveObject {

    private static final Random random = new Random(System.currentTimeMillis());

    @Override
    public Result<String> makeString(int count, char fillChar) {
        char[] buf = new char[count];
        for (int i = 0; i < count; i++) {
            buf[i] = fillChar;

            slowly(10);
        }

        return new RealResult<>(new String(buf));
    }

    private void slowly(int mills) {
        try {
            TimeUnit.MILLISECONDS.sleep(mills);
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }
    }

    @Override
    public void displayString(String text) {
        printColsole(text);
        slowly(5);
    }

    private void printColsole(String text) {
        System.out.println(text);
    }
}
