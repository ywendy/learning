package com.ypb.concurrent.stage2.chapter9;

import com.google.common.collect.Lists;
import java.util.LinkedList;

import lombok.extern.slf4j.Slf4j;

/**
 * @className RequestQueue
 * @description 保存Request的队列
 * @author yangpengbing
 * @date 23:23 2019/4/23
 * @version 1.0.0
 */
@Slf4j
public class RequestQueue {

    private final LinkedList<Request> queues = Lists.newLinkedList();

    public Request getRequest() {
        synchronized (queues) {
            while (queues.size() <= 0) {
                try {
                    queues.wait(100);
                } catch (InterruptedException e) {
                    log.debug(e.getMessage(), e);

                    return null;
                }
            }

            return queues.removeFirst();
        }
    }

    public void putRequest(Request request) {
        synchronized (queues) {
            queues.addLast(request);
            queues.notifyAll();
        }
    }

    public int getSize() {
        return queues.size();
    }
}
