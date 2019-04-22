package com.ypb.concurrent.stage2.chapter6;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SharedDate {

    private final char[] buffer;
    private final ReadWriteLock lock = new ReadWriteLock();

    public SharedDate(int size) {
        buffer = new char[size];

        populate(buffer, '*');
    }

    public char[] read() throws InterruptedException {
        try {
            lock.readLock();
            return this.doRead();
        }finally {
            lock.readUnLock();
        }
    }

    public void write(char c) throws InterruptedException {
        try {
            lock.writeLock();
            this.doWrite(c);
        }finally {
            lock.writeUnLock();
        }
    }

    private void doWrite(char c) {
        populate(buffer, c);
    }

    private char[] doRead() {
        char[] newBuffer = new char[buffer.length];
        System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);

        slowly(50);

        return newBuffer;
    }

    private void slowly(int miss) {
        try {
            TimeUnit.MILLISECONDS.sleep(miss);
        } catch (InterruptedException e) {
            log.debug(e.getMessage(), e);
        }
    }

    private void populate(char[] buffer, char c) {
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = c;
        }
    }
}
