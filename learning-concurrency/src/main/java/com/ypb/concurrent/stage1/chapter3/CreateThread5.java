package com.ypb.concurrent.stage1.chapter3;

public class CreateThread5 {

    private static int counter = 0;

    public static void main(String[] args) {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            ++counter;
            new Thread(()-> add(1)).start();
        }

        System.out.println("Total created thread nums =>" + counter);
    }

    private static void add(int i) {
        add(i + 1);
    }
}
