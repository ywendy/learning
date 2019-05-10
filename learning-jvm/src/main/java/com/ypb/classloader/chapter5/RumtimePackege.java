package com.ypb.classloader.chapter5;

public class RumtimePackege {

    public static void main(String[] args) throws ClassNotFoundException {
        SimpleClassLoader loader = new SimpleClassLoader();

        String name = "com.ypb.classloader.chapter5.SimpleObject";
        Class<?> aClass = loader.loadClass(name);

        System.out.println("aClass = " + aClass);
    }
}
