package com.ypb.concurrent.stage2.chapter18;

public class RealResult<T> implements Result<T> {

    private final String resultValue;

    public RealResult(String resultValue) {
        this.resultValue = resultValue;
    }

    @Override
    public T getResultValue() {
        return (T) resultValue;
    }
}
