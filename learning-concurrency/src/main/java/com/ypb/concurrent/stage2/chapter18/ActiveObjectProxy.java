package com.ypb.concurrent.stage2.chapter18;

public class ActiveObjectProxy implements ActiveObject{

    private final SchedulerThread thread;
    private final Servant servant;

    public ActiveObjectProxy(SchedulerThread thread, Servant servant) {
        this.thread = thread;
        this.servant = servant;
    }

    @Override
    public Result makeString(int count, char fillChar) {
        FutureResult result = new FutureResult();

        MethodRequest request = new MakingStringRequest(servant, result, count, fillChar);
        thread.invoke(request);

        return result;
    }

    @Override
    public void displayString(String text) {
        MethodRequest request = new DisplayStringRequest(servant, text);

        thread.invoke(request);
    }
}
