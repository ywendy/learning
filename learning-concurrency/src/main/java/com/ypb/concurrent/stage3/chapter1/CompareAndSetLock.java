package com.ypb.concurrent.stage3.chapter1;

import java.util.concurrent.atomic.AtomicInteger;

public class CompareAndSetLock {

    private final AtomicInteger ai = new AtomicInteger(0);
    private Thread currentThread;

    public void tryLock() throws GetLockException {
        boolean success = ai.compareAndSet(0, 1);
        if (!success) {
            throw new GetLockException("get the lock failed..");
        }

        currentThread = Thread.currentThread();
    }

    public void unLock() {
        if (0 == ai.get()) {
            return;
        }

        if (currentThread == Thread.currentThread()) {
            ai.compareAndSet(1, 0);
        }
    }

    public class GetLockException extends Exception {
        public GetLockException(String message) {
            super(message);
        }
    }
}
