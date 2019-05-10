package com.ypb.classloader.chapter3;

public class MyClassLoaderTest2 {

    public static void main(String[] args) throws ClassNotFoundException {
        test1();
        printSeparator();

        test3();
        printSeparator();

        test2();
    }

    /**
     * 虽然两个类加载器加载的是用一个class文件，但是命名空间不同，两个class的hashcode不相同
     * @throws ClassNotFoundException
     */
    private static void test3() throws ClassNotFoundException {
        MyClassLoader loader1 = new MyClassLoader("myClassloader1");
        MyClassLoader loader2 = new MyClassLoader("myClassloader2");

        String dir = "E:\\temp\\customclassloader\\classloader3\\";
        loader2.setDir(dir);

        String name = "com.ypb.classloader.chapter3.MyObject";
        Class<?> aClass = loader1.loadClass(name);
        Class<?> bclass = loader2.loadClass(name);

        System.out.println("aClass.hashCode() = " + aClass.hashCode());
        System.out.println("bclass.hashCode() = " + bclass.hashCode());
    }

    /**
     * 测试需要加载的文件不存在，加载报错
     * @throws ClassNotFoundException
     */
    private static void test2() throws ClassNotFoundException {
        MyClassLoader loader1 = new MyClassLoader("myClassLoader-1");
        MyClassLoader loader2 = new MyClassLoader("myClassLoader-2");

        String dir = "E:\\temp\\customclassloader\\classloader2\\";
        loader2.setDir(dir);

        String name = "com.ypb.classloader.chapter3.MyObject";
        Class<?> clazz = loader2.loadClass(name);
        System.out.println("clazz = " + clazz);
    }

    private static void printSeparator() {
        System.out.println("------------------------");
    }

    /**
     * 设置loader2的父加载器是loader1，加载MyObject对象， classloader2目录下面没有
     * @throws ClassNotFoundException
     */
    private static void test1() throws ClassNotFoundException {
        MyClassLoader loader1 = new MyClassLoader("myClassLoader-1");
        MyClassLoader loader2 = new MyClassLoader("myClassLoader-2", loader1);

        String dir = "E:\\temp\\customclassloader\\classloader2\\";
        loader2.setDir(dir);

        String name = "com.ypb.classloader.chapter3.MyObject";
        Class<?> clazz = loader2.loadClass(name);
        System.out.println("clazz = " + clazz);
        System.out.println("((MyClassLoader) clazz.getClassLoader()).getClassLoaderName() = " + ((MyClassLoader) clazz.getClassLoader()).getClassLoaderName());
    }
}
