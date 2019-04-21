package com.ypb.concurrent.stage2.chapter5;

public class Clinet {

    public static void main(String[] args) {
        Gate gate = new Gate();

        Thread bj = new User("Baobao", "Beijing", gate);
        Thread sh = new User("ShangLao", "Shanghai", gate);
        Thread gz = new User("GuangLao", "Guangzhou", gate);

        bj.start();
        sh.start();
        gz.start();
    }
}
