package com.ypb.classloader.chapter6;

import com.ypb.classloader.chapter3.MyClassLoader;

public class ThreadContextClassLoaderTest {

    public static void main(String[] args) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        System.out.println("loader = " + loader);

        Thread.currentThread().setContextClassLoader(new MyClassLoader());
        loader = Thread.currentThread().getContextClassLoader();
        System.out.println("loader = " + loader);
    }
}
