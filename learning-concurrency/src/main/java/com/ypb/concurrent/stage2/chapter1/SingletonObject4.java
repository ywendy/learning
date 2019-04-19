package com.ypb.concurrent.stage2.chapter1;
/**
 * @className SingletonObject4
 * @description 对SingletonObject3的改进
 * @author yangpengbing
 * @date 22:04 2019/4/19
 * @version 1.0.0
 */
public class SingletonObject4 {

    private static SingletonObject4 instance;

    private SingletonObject4(){}

    public static SingletonObject4 getInstance() {
        if (instance == null) {
            synchronized (SingletonObject4.class) {
                if (instance == null) {
                    instance = new SingletonObject4();
                }
            }
        }

        return instance;
    }

    /**************************** 学习笔记(2019年4月19日) ******************************/
//    double check方法
//    可能会造成空指针的问题， 是因为在new SingletonObject4()的方法的时候，如果对象初始化完成，但是对象里面的属性赋值没有成功，
//    使用的这个对象获取属性的时候，会报空指针异常
}
