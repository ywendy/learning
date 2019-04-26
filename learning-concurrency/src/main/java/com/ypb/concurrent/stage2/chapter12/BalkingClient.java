package com.ypb.concurrent.stage2.chapter12;

public class BalkingClient {

    public static void main(String[] args) {
        String fileName = "E:\\gitproject\\learning\\learning-concurrency\\src\\main\\java\\com\\ypb\\concurrent\\stage2\\chapter12\\baling.txt";
        BalkingData data = new BalkingData(fileName, "====begin===");

        Thread ct = new CustomerThread(data);
        Thread wt = new WaiterThread(data);

        ct.start();
        wt.start();
    }
}
