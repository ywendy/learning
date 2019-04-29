package com.ypb.concurrent.stage2.chapter18;
/**
 * @className MethodRequest
 * @description 对应ActiveObject类的中每一个方法
 * @author yangpengbing
 * @date 21:56 2019/4/29
 * @version 1.0.0
 */
public abstract class MethodRequest {

    protected final FutureResult futureResult;
    protected final Servant servant;

    public MethodRequest(Servant servant) {
        this(servant, null);
    }

    public MethodRequest(Servant servant, FutureResult futureResult) {
        this.servant = servant;
        this.futureResult = futureResult;
    }

    public abstract void execuet();
}
