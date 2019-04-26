package com.ypb.concurrent.stage2.chapter13;

import lombok.Getter;

public class Message {

    @Getter
    private String message;

    public Message(String message) {
        this.message = message;
    }
}
