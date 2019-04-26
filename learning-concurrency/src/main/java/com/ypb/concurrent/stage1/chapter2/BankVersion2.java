package com.ypb.concurrent.stage1.chapter2;

public class BankVersion2 {

    public static void main(String[] args) {
        TicketWindowRunnable twr = new TicketWindowRunnable();
        Thread t1 = new Thread(twr, "1号柜台");
        Thread t2 = new Thread(twr, "2号柜台");
        Thread t3 = new Thread(twr, "3号柜台");

        t1.start();
        t2.start();
        t3.start();
    }
}
