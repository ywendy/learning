package com.ypb.concurrent.stage2.chapter6;

public class ReadWriteLockClient {

    public static void main(String[] args) {
        final SharedDate date = new SharedDate(10);

        new ReadWorker(date).start();
        new ReadWorker(date).start();
        new ReadWorker(date).start();
        new ReadWorker(date).start();

        new WriteWorker(date, "qwertyuiop").start();
        new WriteWorker(date, "QWERTYUIOP").start();
    }
}
