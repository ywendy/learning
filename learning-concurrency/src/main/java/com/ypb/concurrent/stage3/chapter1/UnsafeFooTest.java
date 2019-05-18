package com.ypb.concurrent.stage3.chapter1;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeFooTest {

    private static Unsafe getUnsafe() throws NoSuchFieldException, IllegalAccessException {
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);

        return (Unsafe) field.get(null);
    }

    public static void main(String[] args) throws Exception {
//        test1();
//        test2();
        test3();
    }

    private static void test3() throws NoSuchFieldException, IllegalAccessException {
        Unsafe unsafe = getUnsafe();

        byte[] bytes = null;
//        unsafe.defineClass

        unsafe.defineAnonymousClass(null, null, null);
    }

    /**
     * 更改变量的值
     */
    private static void test2() throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        Guard guard = new Guard();
        guard.work();

        System.out.println("-----------------");

        Unsafe unsafe = getUnsafe();
        guard = (Guard) unsafe.allocateInstance(Guard.class);
        Field field = Guard.class.getDeclaredField("accessAllowed");

        unsafe.putInt(guard, unsafe.objectFieldOffset(field), 42);
        guard.work();
    }

    /**
     * 使用Unsafe构造Simple对象，不调用构造方法<init>方法
     */
    private static void test1() throws Exception {
        Simple simple = new Simple();
        printConsole(simple);

        System.out.println("--------------------");

        Unsafe unsafe = getUnsafe();
        simple = (Simple) unsafe.allocateInstance(Simple.class);
        printConsole(simple);
    }

    private static void printConsole(Simple simple) {
        System.out.println("simple.getClass() = " + simple.getClass());
        System.out.println("simple.get() = " + simple.get());
        System.out.println("simple.getClass().getClassLoader() = " + simple.getClass().getClassLoader());
    }

    private static class Simple {
        private int i = 0;

        private Simple() {
            i = 1;
            System.out.println("simple init.");
        }

        public int get() {
            return i;
        }
    }

    private static class Guard {
        private int accessAllowed = 1;

        private boolean allow() {
            return accessAllowed == 42;
        }

        public void work() {
            if (allow()) {
                System.out.println("I am working by allowed..");
            }
        }
    }
}
