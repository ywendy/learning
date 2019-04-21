package com.ypb.concurrent.stage2.chapter5;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class User extends Thread {

    private final String name;
    private final String address;
    private final Gate gate;

    public User(String name, String address, Gate gate) {
        this.name = name;
        this.address = address;
        this.gate = gate;
    }

    @Override
    public void run() {
        System.out.println(name + " BEGIN");
        try {
            while (true) {
                gate.pass(name, address);

                TimeUnit.MILLISECONDS.sleep(1_000);
            }
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }
    }
}
