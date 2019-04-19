package com.ypb.concurrent.stage2.chapter1;
/**
 * @className SingletonObject2
 * @description 懒汉式
 * @author yangpengbing
 * @date 21:54 2019/4/19
 * @version 1.0.0
 */
public class SingletonObject2 {

    private static SingletonObject2 instance = null;

    private SingletonObject2(){}

    public static SingletonObject2 getInstance() {
        if (instance == null) {
            instance = new SingletonObject2();
        }

        return instance;
    }

    /**************************** 学习笔记(2019年4月19日) ******************************/
//    成员变量不能定义为final
//    不能保证对象在jvm中是唯一的，多线程并发问题
}
