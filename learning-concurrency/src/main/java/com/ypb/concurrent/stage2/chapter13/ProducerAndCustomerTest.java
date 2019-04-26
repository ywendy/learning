package com.ypb.concurrent.stage2.chapter13;

public class ProducerAndCustomerTest {

    public static void main(String[] args) {
        MessageQueue queue = new MessageQueue();

        int pts = 3;
        for (int i = 0; i < pts; i++) {
            new ProducerThread(queue, i).start();
        }

        int cts = 2;
        for (int i = 0; i < cts; i++) {
            new CustomerThread(queue, i).start();
        }
    }
}
