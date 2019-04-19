package com.ypb.concurrent.stage2.chapter1;
/**
 * @className SingletonObject6
 * @description 使用内部类的方式创建单例对象
 * @author yangpengbing
 * @date 22:21 2019/4/19
 * @version 1.0.0
 */
public class SingletonObject6 {

    private SingletonObject6(){}

    private static class InstanceHolder {
        private static final SingletonObject6 INSTANCE = new SingletonObject6();
    }

    public static SingletonObject6 getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**************************** 学习笔记(2019年4月19日) ******************************/
//   使用了jvm类加载的特性， 类只有在第一次使用的时候才会加载
}
