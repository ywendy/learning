package com.ypb.concurrent.stage2.chapter4;

public class BinaryObserve extends Observe {

    public BinaryObserve(Subject subject) {
        super(subject);
    }

    @Override
    void update() {
        System.out.println("binary string:" + Integer.toBinaryString(subject.getStatus()));
    }
}
