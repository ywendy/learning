package com.ypb.concurrent.stage2.chapter9;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientThread extends Thread {

    private final RequestQueue queue;
    private final String sendValue;
    private final Random random;

    public ClientThread(RequestQueue queue, String sendValue) {
        this.queue = queue;
        this.sendValue = sendValue;
        this.random = new Random(System.currentTimeMillis());
    }

    @Override
    public void run() {
        int times = 10;
        for (int i = 0; i < times; i++) {
            Request request = initRequest(sendValue, i);

            queue.putRequest(request);
            printRequest(request);
            try {
                slowly(500);
            } catch (InterruptedException e) {
                log.debug(e.getMessage(), e);

                break;
            }
        }
    }

    private void printRequest(Request request) {
        System.out.println("clent send request -> " + request.getValue());
    }

    private Request initRequest(String sendValue, int i) {
        return new Request(sendValue + i);
    }

    private void slowly(int mills) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(mills);
    }
}
