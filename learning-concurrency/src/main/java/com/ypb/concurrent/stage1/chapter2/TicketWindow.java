package com.ypb.concurrent.chapter2;

/**
 * @author yangpengbing
 * @version 1.0.0
 * @className TicketWindow
 * @description 使用多线程方式模拟银行排队叫号
 * @date 23:29 2019/4/4
 */
public class TicketWindow extends Thread {

    /**
     * 柜台的名称
     */
    private final String name;
    /**
     * 最大叫号数
     */
    private static final int MAX = 50;

    /**
     * 当前叫号数
     */
    private int index = 1;

    public TicketWindow(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        while (index <= MAX) {
            System.out.println("柜台: " + name + ", 当前的号码是: " + index++);
        }
    }
}
