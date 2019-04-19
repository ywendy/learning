package com.ypb.concurrent.stage2.chapter1;
/**
 * @className SingletonObject1
 * @description 饿汉式
 * @author yangpengbing
 * @date 21:48 2019/4/19
 * @version 1.0.0
 */
public class SingletonObject1 {

    private static final SingletonObject1 instance = new SingletonObject1();

    private SingletonObject1(){}

    public static SingletonObject1 getInstance() {
        return instance;
    }

    /**************************** 学习笔记(2019年4月19日) ******************************/
//    保证了单例的要求，jvm中只有一个SingletonObject1对象实例
//    缺点是jvm启动后立即创建对象，程序可能需要获取这个对象，浪费资源。
}
