package com.ypb.concurrent.stage2.chapter8;

public class SyncFutrue<T> implements Future<T> {

    private volatile boolean done;
    private T result;

    public void done(T result) {
        synchronized (this) {
            this.result = result;
            done = true;
            this.notifyAll();
        }
    }

    @Override
    public T get() throws InterruptedException {
        synchronized (this) {
            while (!done) {
                this.wait();
            }
        }
        return result;
    }
}
