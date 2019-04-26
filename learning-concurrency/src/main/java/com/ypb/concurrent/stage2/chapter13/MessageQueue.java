package com.ypb.concurrent.stage2.chapter13;

import com.google.common.collect.Lists;

import java.util.LinkedList;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yangpengbing
 * @version 1.0.0
 * @className MessageQueue
 * @description 消息队列
 * @date 21:37 2019/4/26
 */
@Slf4j
public class MessageQueue {

    private final LinkedList<Message> queue = Lists.newLinkedList();
    private static final int DEFAULT_MAX_SIZE = 100;
    private int size = 0;

    public MessageQueue() {
        this(DEFAULT_MAX_SIZE);
    }

    public MessageQueue(int size) {
        this.size = size;
    }

    public void put(Message message) throws InterruptedException {
        synchronized (queue) {
            // 队列满了，阻塞
            while (queue.size() > size) {
                queue.wait();
            }

            queue.addLast(message);
            queue.notifyAll();
        }
    }
    
    public Message take() throws InterruptedException {
        synchronized (queue) {
            while (queue.isEmpty()) {
                queue.wait();
            }

            Message message = queue.removeFirst();
            queue.notifyAll();
            return message;
        }
    }
}
