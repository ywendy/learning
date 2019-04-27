package com.ypb.concurrent.stage2.chapter15;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class Message {

    @Getter
    private final String value;

    public Message(String value) {
        this.value = value;
    }
}
