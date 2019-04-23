package com.ypb.concurrent.stage2.chapter1;

import java.util.stream.IntStream;

/**
 * @className SingletonObject4
 * @description 对SingletonObject3的改进
 * @author yangpengbing
 * @date 22:04 2019/4/19
 * @version 1.0.0
 */
public class SingletonObject4 {

    private static SingletonObject4 instance;

    private final Object obj1;
    private final Object obj2;
    private final Object obj3;
    private final Object obj4;
    private final Object obj5;
    private final Object obj6;
    private final Object obj7;
    private final Object obj8;
    private final Object obj9;
    private final Object obj10;
    private final Object obj11;
    private final Object obj12;
    private final Object obj13;
    private final Object obj15;
    private final Object obj16;
    private final Object obj17;
    private final Object obj14;

    private SingletonObject4(){
        this.obj1 = new Object();
        this.obj2 = new Object();
        this.obj3 = new Object();
        this.obj4 = new Object();
        this.obj5 = new Object();
        this.obj6 = new Object();
        this.obj7 = new Object();
        this.obj8 = new Object();
        this.obj9 = new Object();
        this.obj10 = new Object();
        this.obj11 = new Object();
        this.obj12 = new Object();
        this.obj13 = new Object();
        this.obj14 = new Object();
        this.obj15 = new Object();
        this.obj16 = new Object();
        this.obj17 = new Object();
    }

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

    public static void main(String[] args) {
        int times = 100000;
        IntStream.range(0, times).forEach(num -> new Thread(SingletonObject4::runTask, "Singleton-" + num).start());
    }

    private static void runTask() {
        int times = 1000;
        for (int i = 0; i < times; i++) {
            SingletonObject4 instance = SingletonObject4.getInstance();

            instance.obj1.toString();
            instance.obj2.toString();
            instance.obj3.toString();
            instance.obj4.toString();
            instance.obj5.toString();
            instance.obj6.toString();
            instance.obj7.toString();
            instance.obj8.toString();
            instance.obj9.toString();
            instance.obj10.toString();
            instance.obj11.toString();
            instance.obj12.toString();
            instance.obj13.toString();
            instance.obj14.toString();
            instance.obj15.toString();
            instance.obj16.toString();
            instance.obj17.toString();

            instance = null;
        }
    }

    /**************************** 学习笔记(2019年4月19日) ******************************/
//    double check方法
//    可能会造成空指针的问题， 是因为在new SingletonObject4()的方法的时候，如果对象初始化完成，但是对象里面的属性赋值没有成功，
//    使用的这个对象获取属性的时候，会报空指针异常

//    instance
//    obj1
//    obj2

//    创建这三个对象的时候会重排序，有可能instance创建成功，obj1和obj2对象没有创建成功，在使用obj1或者obj2的属性的时候，出出现空指针异常
}
