package com.ypb.concurrent.stage1.chapter10;

import java.util.Collection;

public interface Lock {

    class TimeOutException extends Exception {

        public TimeOutException(String message) {
            super(message);
        }
    }

    void lock() throws InterruptedException;

    void lock(long mills) throws InterruptedException, TimeOutException;

    void unLock();

    Collection<Thread> getBlockedThreads();

    int blockedThreads();
}
