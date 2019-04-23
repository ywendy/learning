package com.ypb.concurrent.stage2.chapter9;

import lombok.Getter;

public class Request {

    @Getter
    private final String value;

    public Request(String value) {
        this.value = value;
    }
}
