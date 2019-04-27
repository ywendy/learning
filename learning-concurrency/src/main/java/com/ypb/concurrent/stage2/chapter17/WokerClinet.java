package com.ypb.concurrent.stage2.chapter17;

public class WokerClinet {

    public static void main(String[] args) {
        final Channel channel = new Channel(5);
        channel.startWorkers();

        TransportThread thread = new TransportThread(channel);
        int tasks = 500;
        for (int i = 0; i < tasks; i++) {
            TransportThread.TransportTask task = thread.new TransportTask(i);


            thread.submit(task);
        }

        thread.shutdown();
    }
}
