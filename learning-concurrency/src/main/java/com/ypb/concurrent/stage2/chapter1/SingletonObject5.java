package com.ypb.concurrent.stage2.chapter1;
/**
 * @className SingletonObject5
 * @description 对SingletonObject4的改进
 * @author yangpengbing
 * @date 22:16 2019/4/19
 * @version 1.0.0
 */
public class SingletonObject5 {

    private static volatile SingletonObject5 instance;

    private SingletonObject5() {}

    public static SingletonObject5 getInstance() {
        if (instance == null) {
            synchronized (SingletonObject5.class) {
                if (instance == null) {
                    instance = new SingletonObject5();
                }
            }
        }

        return instance;
    }

    /**************************** 学习笔记(2019年4月19日) ******************************/
//   instance成员变量使用volatile修饰，保证了可见性，禁止对其进程指令重排序，但是不保证原子性
//   volatile保证了变量在进行读的操作时，所有的写操作都已经完成。
}
