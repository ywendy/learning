package com.ypb.concurrent.stage2.chapter17;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

/**
 * @className Channel
 * @description  定义流水线
 * @author yangpengbing
 * @date 22:36 2019/4/27
 * @version 1.0.0
 */
@Slf4j
public class Channel {

    private static final int MAX_REQUEST = 100;
    private final Request[] requests;
    private final WokerThread[] wokerThreads;
    private int head;
    private int tail;
    private int count;

    public Channel(int workers) {
        requests = new Request[MAX_REQUEST];
        wokerThreads = new WokerThread[workers];
        initWorkerThreads();
    }

    private void initWorkerThreads() {
        for (int i = 0; i < wokerThreads.length; i++) {
            wokerThreads[i] = new WokerThread("woker-" + i, this);
        }
    }
    
    public void startWorkers() {
        Arrays.asList(wokerThreads).forEach(WokerThread::start);
    }

    public synchronized void put(Request request) {
        while (count >= MAX_REQUEST) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                log.debug(e.getMessage(), e);
            }
        }

        requests[tail] = request;
        tail = (tail + 1) % requests.length;
        count++;
        this.notifyAll();
    }

    public synchronized Request take() {
        while (count <= 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                log.debug(e.getMessage(), e);
            }
        }

        Request request = requests[head];
        head = (head + 1) % requests.length;
        count--;
        this.notifyAll();
        return request;
    }

    public synchronized void stopWorkers() {
        while (count > 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                log.debug(e.getMessage(), e);
            }
        }

        Arrays.asList(wokerThreads).forEach(WokerThread::stopWorker);
        this.notifyAll();
    }
}
