package com.ypb.classloader.chapter4;

import com.ypb.classloader.chapter3.MyClassLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        String dir = "E:\\temp\\customclassloader\\classloader4\\";
        String name = "com.ypb.classloader.chapter3.MyObject";

        ClassLoader loader = new DecryptClassLoader();
        ((DecryptClassLoader) loader).setDir(dir);
        Class<?> aClass = loader.loadClass(name);

        System.out.println("aClass = " + aClass);

        Object obj = aClass.newInstance();
        Method method = aClass.getMethod("hello", new Class<?>[]{});
        System.out.println("method.invoke(obj, new Object[]{}) = " + method.invoke(obj, new Object[]{}));

//        test1(dir, name);
    }

    /**
     * Incompatible magic value 889275713
     * @param dir
     * @param name
     * @throws ClassNotFoundException
     */
    private static void test1(String dir, String name) throws ClassNotFoundException {
        ClassLoader loader = new MyClassLoader();

        ((MyClassLoader) loader).setDir(dir);

        Class<?> aClass = loader.loadClass(name);
        System.out.println("aClass = " + aClass);
    }
}
