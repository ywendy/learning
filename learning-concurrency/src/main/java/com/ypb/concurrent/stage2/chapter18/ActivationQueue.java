package com.ypb.concurrent.stage2.chapter18;

import com.google.common.collect.Lists;

import java.util.LinkedList;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ActivationQueue {

    private static final int MAX_QUEUE_SIZE = 100;
    private final LinkedList<MethodRequest> queues;

    public ActivationQueue() {
        queues = Lists.newLinkedList();
    }

    public synchronized void put(MethodRequest request) {
        while (queues.size() >= MAX_QUEUE_SIZE) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                log.debug(e.getMessage(), e);
            }
        }

        queues.addLast(request);
        this.notifyAll();
    }

    public synchronized MethodRequest take() {
        while (queues.isEmpty()) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                log.debug(e.getMessage(), e);
            }
        }

        MethodRequest request = queues.removeFirst();
        this.notifyAll();

        return request;
    }
}
