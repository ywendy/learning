package com.ypb.classloader.chapter3;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        MyClassLoader loader = new MyClassLoader("myClassLoader");

        String name = "com.ypb.classloader.chapter3.MyObject";
        Class<?> clazz = loader.loadClass(name);

        System.out.println("clazz.getClassLoader() = " + clazz.getClassLoader());
        System.out.println("loader.getClass().getClassLoader() = " + loader.getClass().getClassLoader());

        Object obj = clazz.newInstance();
        Method method = obj.getClass().getMethod("hello", new Class[]{});
        Object result = method.invoke(obj, new Object[]{});
        System.out.println("result = " + result);
    }
}
