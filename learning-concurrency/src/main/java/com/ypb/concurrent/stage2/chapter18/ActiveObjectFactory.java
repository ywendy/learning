package com.ypb.concurrent.stage2.chapter18;

public final class ActiveObjectFactory {

    private ActiveObjectFactory() {}

    public static ActiveObject createActiveObject() {
        ActivationQueue queue = new ActivationQueue();
        SchedulerThread thread = new SchedulerThread(queue);

        Servant servant = new Servant();

        ActiveObjectProxy proxy = new ActiveObjectProxy(thread, servant);

        thread.start();
        return proxy;
    }
}
