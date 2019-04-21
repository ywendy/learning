package com.ypb.concurrent.stage2.chapter4;

public class OctalObserve extends Observe {

    public OctalObserve(Subject subject) {
        super(subject);
    }

    @Override
    void update() {
        System.out.println("octal string:" + Integer.toOctalString(subject.getStatus()));
    }
}
