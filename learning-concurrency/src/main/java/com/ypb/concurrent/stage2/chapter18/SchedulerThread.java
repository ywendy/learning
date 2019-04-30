package com.ypb.concurrent.stage2.chapter18;

public class SchedulerThread extends Thread {

    private final ActivationQueue queue;

    public SchedulerThread(ActivationQueue queue) {
        this.queue = queue;
    }

    public void invoke(MethodRequest request) {
        queue.put(request);
    }

    @Override
    public void run() {
        while (true) {
            queue.take().execute();
        }
    }
}
