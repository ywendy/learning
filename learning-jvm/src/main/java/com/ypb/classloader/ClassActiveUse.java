package com.ypb.classloader;

import java.util.Random;

public class ClassActiveUse {

    static {
        System.out.println("ClassActiveUse");
    }

    public static void main(String[] args) {
        // 子类访问父类的static变量，不会导致子类初始化
//        System.out.println(Child.age);

        // 定义引用类型的数组，不会初始化类
//        Obj[] arrs = new Obj[10];

        // final修饰的常量会在编译期间保存的常量池中，不会初始化类
        System.out.println(Obj.salary);
        // final修饰的复杂类型，在编译期间无法计算出结果的，会初始化类
        System.out.println(Obj.x);
    }
}

class Child extends Obj {

    static {
        System.out.println("Child 被初始化了.");
    }
}

class Obj {

    public static final long salary = 10000;
    public static final int x = new Random().nextInt(10);

    public static int age = 10;

    static {
        System.out.println("Obj 被初始化了.");
    }
}

interface I {

    /**
     * 接口中定义的常量都是public static final的
     */
    int a = 10;
}