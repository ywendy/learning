package com.ypb.classloader.chapter5;

public class Namespace {

    public static void main(String[] args) throws ClassNotFoundException {
        ClassLoader loader = Namespace.class.getClassLoader();

        Class<?> aClass = loader.loadClass("java.lang.String");
        Class<?> bClass = loader.loadClass("java.lang.String");

        System.out.println("aClass.hashCode() = " + aClass.hashCode());
        System.out.println("bClass.hashCode() = " + bClass.hashCode());

        // 同一个类加载器加载相同的类， 在内存中是单例的
    }
}
