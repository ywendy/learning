package com.ypb.concurrent.stage2.chapter18;
/**
 * @className ActiveObject
 * @description 接受异步消息的主动对象
 * @author yangpengbing
 * @date 21:50 2019/4/29
 * @version 1.0.0
 */
public interface ActiveObject {

    Result<String> makeString(int count, char fillChar);

    void displayString(String text);
}
