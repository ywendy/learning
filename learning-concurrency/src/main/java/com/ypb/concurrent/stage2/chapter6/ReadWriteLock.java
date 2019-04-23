package com.ypb.concurrent.stage2.chapter6;

import lombok.extern.slf4j.Slf4j;

/**
 * @className ReadWriteLock
 * @description 读写锁
 * @author yangpengbing
 * @date 21:14 2019/4/22
 * @version 1.0.0
 */
@Slf4j
public class ReadWriteLock {

    /**
     * 正在读的数量
     */
    private int readingReaders;
    /**
     * 等待读的数量
     */
    private int waitingReaders;
    /**
     * 正在写的数量
     */
    private int writingWriters;
    /**
     * 等待写的数量
     */
    private int waitingWriters;
    /**
     * 设置线程更喜欢写, 是读和写尽量公平
     */
    private boolean preferWiter;

    public ReadWriteLock() {
        this(Boolean.TRUE);
    }

    public ReadWriteLock(Boolean preferWriter) {
        this.preferWiter = preferWriter;
    }

    public synchronized void readLock() throws InterruptedException {
        waitingReaders++;
        try {
            while (writingWriters > 0 || (preferWiter && writingWriters > 0)) {
                this.wait();
            }
            readingReaders++;
        }finally {
            waitingReaders--;
        }
    }

    public synchronized void readUnLock() {
        this.readingReaders--;
        this.notifyAll();
    }

    public synchronized void writeLock() throws InterruptedException {
        waitingWriters++;
        try {
            while (readingReaders > 0 || writingWriters > 0) {
                this.wait();
            }
            writingWriters++;
        }finally {
            waitingWriters--;
        }
    }

    public synchronized void writeUnLock() {
        writingWriters--;
        this.notifyAll();
    }

    /**************************** 学习笔记(2019年4月22日) ******************************/
//    读写锁
//    +---------------------+
//    +      | read | write |
//    +---------------------+
//    + read | N    | Y     |
//    +---------------------+
//    + write| Y    | Y     |
//    +---------------------+
}
