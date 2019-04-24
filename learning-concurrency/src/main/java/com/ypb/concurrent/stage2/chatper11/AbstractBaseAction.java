package com.ypb.concurrent.stage2.chatper11;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public abstract class AbstractBaseAction implements BaseAction {

    protected final Random random = new Random(System.currentTimeMillis());

    protected void slowly(int mills) throws InterruptedException {
        mills = random.nextInt(mills);
        TimeUnit.MILLISECONDS.sleep(mills);
    }
}
