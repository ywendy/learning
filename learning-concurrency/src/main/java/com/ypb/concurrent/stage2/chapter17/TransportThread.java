package com.ypb.concurrent.stage2.chapter17;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransportThread {

    private static final ExecutorService pool;
    private static final Random random = new Random(System.currentTimeMillis());

    static {
        ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("transport-%d").build();
        pool = Executors.newFixedThreadPool(10, factory);
    }

    private final Channel channel;

    public TransportThread(Channel channel) {
        this.channel = channel;
    }

    public void submit(TransportTask task) {
        pool.submit(task);
    }

    public void shutdown() {
        channel.stopWorkers();
        pool.shutdown();
    }

    protected class TransportTask implements Runnable {

        private final int num;

        public TransportTask(int num) {
            this.num = num;
        }

        @Override
        public void run() {
            Request request = new Request(Thread.currentThread().getName(), num);

            channel.put(request);

            try {
                slowly(1000);
            } catch (InterruptedException e) {
                log.debug(e.getMessage(), e);
            }
        }

        private void slowly(int mills) throws InterruptedException {
            TimeUnit.MILLISECONDS.sleep(random.nextInt(mills));
        }
    }
}
