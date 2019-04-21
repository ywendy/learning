package com.ypb.concurrent.stage2.chapter5;

import java.math.BigDecimal;

/**
 * @className Gate
 * @description 共享资源
 * @author yangpengbing
 * @date 22:57 2019/4/21
 * @version 1.0.0
 */
public class Gate {

    private int counter;
    private String name;
    private String address;

    public synchronized void pass(String name, String address) {
        this.counter++;
        this.name = name;
        this.address = address;

        verify();
    }

    private void verify() {
        if (name.charAt(BigDecimal.ZERO.intValue()) != address.charAt(BigDecimal.ZERO.intValue())) {
            print();
        }
    }

    private void print() {
        System.out.println("*****BROKEN******" + toString());
    }

    @Override
    public String toString() {
        return "No." + this.counter + ":" + name + "," + address;
    }
}
