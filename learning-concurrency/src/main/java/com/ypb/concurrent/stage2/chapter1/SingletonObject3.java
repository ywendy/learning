package com.ypb.concurrent.stage2.chapter1;
/**
 * @className SingletonObject3
 * @description 对懒汉式单例进行改造
 * @author yangpengbing
 * @date 21:58 2019/4/19
 * @version 1.0.0
 */
public class SingletonObject3 {

    private static SingletonObject3 instance;

    private SingletonObject3(){}

    public static synchronized SingletonObject3 getInstance() {
        if (instance == null) {
            instance = new SingletonObject3();
        }

        return instance;
    }

    /**************************** 学习笔记(2019年4月19日) ******************************/
//    方法上面添加了synchronized同步锁，虽然解决了饿汉式会创建多个实例的问题，但是每次调用都会加锁，效率太低
}
