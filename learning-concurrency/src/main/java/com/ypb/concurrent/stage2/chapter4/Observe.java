package com.ypb.concurrent.stage2.chapter4;

public abstract class Observe {

    protected final Subject subject;

    public Observe(Subject subject) {
        this.subject = subject;
        subject.attach(this);
    }

    abstract void update();
}
