package com.ypb.concurrent.stage2.chapter15;

import java.util.stream.IntStream;

public class PerThreadClient {

    public static void main(String[] args) {
        final MessageHandler handler = new MessageHandler();
        IntStream.rangeClosed(1, 10).mapToObj(i -> new Message(String.valueOf(i))).forEach(handler::request);
//        handler.shutdown();

        IntStream.rangeClosed(1, 20).mapToObj(i -> new Message(String.valueOf(i))).forEach(handler::request);
        handler.shutdown();
    }
}
