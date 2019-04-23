package com.ypb.concurrent.stage2.chapter9;

public class SuspensionClient {

    public static void main(String[] args) throws InterruptedException {
        RequestQueue queue = new RequestQueue();

        ServerThread serverThread = new ServerThread(queue);
        ClientThread clientThread = new ClientThread(queue, "YangPengBing");

        serverThread.start();
        clientThread.start();

        Thread.sleep(20_000);
        serverThread.shutdown();
    }
}
