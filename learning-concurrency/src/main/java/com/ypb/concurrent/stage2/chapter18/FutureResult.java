package com.ypb.concurrent.stage2.chapter18;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FutureResult<T> implements Result<T> {

    private boolean ready = false;
    private Result<T> result;

    public synchronized void setResut(Result<T> result) {
        this.result = result;
        this.ready = true;
        this.notifyAll();
    }

    @Override
    public synchronized T getResultValue() {
        while (!ready) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                log.debug(e.getMessage(), e);
            }
        }

        return result.getResultValue();
    }
}
