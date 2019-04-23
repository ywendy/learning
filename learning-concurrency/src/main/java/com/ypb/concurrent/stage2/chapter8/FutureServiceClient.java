package com.ypb.concurrent.stage2.chapter8;

import com.google.common.base.Stopwatch;

public class FutureServiceClient {

    public static void main(String[] args) throws InterruptedException {
        Stopwatch sw = Stopwatch.createStarted();

        FutureService service = new FutureService();

        Future future = service.submit(FutureServiceClient::futureTask);

        System.out.println("=============");
        System.out.println("do something other..");
        System.out.println("=============");
        Thread.sleep(5_000);
        System.out.println(future.get());

        sw.stop();
        System.out.println(sw.toString());

        System.out.println("--------------------传统的future模式-----------------");

        sw.start();

        service.submit(FutureServiceClient::futureTask, System.out::println);

        sw.stop();
        System.out.println(sw);
    }

    private static String futureTask() {
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "YangPengBing Good!";
    }
}
