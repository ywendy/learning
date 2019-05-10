package com.ypb.classloader.chapter5;

public class SimpleClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException {
        SimpleClassLoader loader = new SimpleClassLoader();

        String name = "com.ypb.classloader.chapter3.MyObject";
        System.out.println("loader.loadClass(name) = " + loader.loadClass(name));

        name = "java.lang.String"; // java.lang.SecurityException: Prohibited package name: java.lang
        System.out.println("loader.loadClass(name) = " + loader.loadClass(name));
    }
}
