package com.ypb.concurrent.stage1.chapter2;
/**
 * @className Bank
 * @description 银行大厅
 * @author yangpengbing
 * @date 23:33 2019/4/4
 * @version 1.0.0
 */
public class Bank {

    public static void main(String[] args) {
        TicketWindow tw = new TicketWindow("1");
        tw.start();

        TicketWindow tw2 = new TicketWindow("2");
        tw2.start();

        TicketWindow tw3 = new TicketWindow("3");
        tw3.start();
    }
}
