package com.ypb.concurrent.stage2.chapter4;

public class ObserveClient {

    public static void main(String[] args) {
        Subject subject = new Subject();

        new BinaryObserve(subject);
        new OctalObserve(subject);

        System.out.println("-----------");
        subject.setStatus(10);
        System.out.println("-----------");
        subject.setStatus(10);

        System.out.println("-----------");
        subject.setStatus(15);
    }
}
