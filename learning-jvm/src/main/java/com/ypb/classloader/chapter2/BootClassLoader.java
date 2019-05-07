package com.ypb.classloader.chapter2;

public class BootClassLoader {

    public static void main(String[] args) throws ClassNotFoundException {
        // 获取根加载器的加载目录
        System.out.println("System.getProperty(\"sun.boot.class.path\") = " + System.getProperty("sun.boot.class.path"));
        // 获取扩展加载器的加载目录
        System.out.println("System.getProperty(\"java.ext.dirs\") = " + System.getProperty("java.ext.dirs"));

        // 获取系统加载器的加载目录

        String className = "com.ypb.classloader.chapter2.SimpleObject";
        Class<?> clazz = Class.forName(className);

        // AppClassLoader
        System.out.println("clazz.getClassLoader() = " + clazz.getClassLoader());

        // ExtClassLoader
        System.out.println("clazz.getClassLoader().getParent() = " + clazz.getClassLoader().getParent());

        // BootstrapClassLoader
        System.out.println("clazz.getClassLoader().getParent().getParent() = " + clazz.getClassLoader().getParent().getParent());
    }
}
