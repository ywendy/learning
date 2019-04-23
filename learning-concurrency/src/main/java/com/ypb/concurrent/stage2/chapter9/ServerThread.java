package com.ypb.concurrent.stage2.chapter9;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerThread extends Thread {

    private volatile boolean closed;

    private final RequestQueue queue;
    private Random random;

    public ServerThread(RequestQueue queue) {
        this.queue = queue;
        this.random = new Random(System.currentTimeMillis());
    }

    @Override
    public void run() {
        while (!closed && queue.getSize() >= 0) {
            Request request = queue.getRequest();

            if (Objects.isNull(request)) {
                continue;
            }

            printRequest(request);

            try {
                slowly(1000);
            } catch (InterruptedException e) {
                log.debug(e.getMessage(), e);
                break;
            }
        }
    }

    public void shutdown() {
        closed = true;
        this.interrupt();
    }

    private void slowly(int mills) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(mills);
    }

    private void printRequest(Request request) {
        System.out.println("Server received request -> " + request.getValue());
    }
}
