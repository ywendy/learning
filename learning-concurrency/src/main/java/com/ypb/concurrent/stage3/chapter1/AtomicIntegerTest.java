package com.ypb.concurrent.stage3.chapter1;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest {

    public static void main(String[] args) {
        AtomicInteger ai = new AtomicInteger();

        System.out.println(ai.getAndIncrement());
    }
}
