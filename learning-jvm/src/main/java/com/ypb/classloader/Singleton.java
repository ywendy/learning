package com.ypb.classloader;

/**
 * @author yangpengbing
 * @version 1.0.0
 * @className Singleton
 * @description 奇怪的例子
 * @date 21:30 2019/5/6
 */
public class Singleton {

//    private static Singleton instance = new Singleton(); // 位置①
    public static int x = 0;
    public static int y;

    private static Singleton instance = new Singleton(); // 位置②

    private Singleton() {
        x++;
        y++;
    }

    public static Singleton getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance();
        System.out.println("singleton.x = " + singleton.x);
        System.out.println("singleton.y = " + singleton.y);
    }

    /**************************** 学习笔记(2019年5月6日) ******************************/
//    代码编写在位置①的地方， 输出的是x的值0，y的值是1
//    代码编写在位置②的地方，输出的是x的值是1，y的值是1

//    分析， jvm加载类的时候，分为三个阶段，1, 加载，2，链接(分为 验证，准备，解析)，3，初始化
//      加载是查询并加载类的二进制数据
//      验证是确保被加载类的正确性
//      准备是为类的静态变量分配内存，并将其初始化为默认的值
//      解析是把类中的符号引用转换为直接引用
//      初始化是为类的静态变量赋予正确的初始值

//      类的初始化其实就是执行类构造器<clinit>方法，这个方法是有类的静态变量和静态代码块注册，顺序是代码中的编写顺序

//    分析编写的位置2的时候
//        在准备阶段
//        x --> 0 y --> 0 instance = null
//        初始化阶段
//        x --> 1 y --> 1 instance = new Singleton();

//    分析编写的位置1的时候
//        在准备阶段
//        x --> 0, y --> 0 instance = null
//        初始化阶段
//        instance = new Singleton();
//        x --> 1，y --> 1
//        后面有执行了 x = 0和赋值操作
//        x = 0
}
