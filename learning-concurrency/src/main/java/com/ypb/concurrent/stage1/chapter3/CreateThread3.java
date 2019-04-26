package com.ypb.concurrent.stage1.chapter3;
/**
 * @className CreateThread3
 * @description 递归调用
 * @author yangpengbing
 * @date 21:44 2019/4/8
 * @version 1.0.0
 */
public class CreateThread3 {

    private static int DEFAULT_VALUE = 0;
    private byte[] bytes = new byte[1024];

    private static int counter = 0;

    private static void add(int i) {
        ++counter;
        add(i + 1);
    }

    public static void main(String[] args) {
        try {
            add(DEFAULT_VALUE);
        } catch (Throwable e) {
            // 需要使用Throwable进行补货Error异常，如果使用Exception是捕获不到Error异常的
            // StackOverflowError
            e.printStackTrace();
            System.out.println("counter = " + counter);
        }

//      counter = 24788
    }
}
