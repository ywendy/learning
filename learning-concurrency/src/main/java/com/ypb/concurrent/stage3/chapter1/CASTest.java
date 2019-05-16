package com.ypb.concurrent.stage3.chapter1;

import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yangpengbing
 * @version 1.0.0
 * @className CASTest
 * @description CAS详解
 * @date 21:44 2019/5/16
 */
@Slf4j
public class CASTest {

    private static int count = 0;
    private static AtomicInteger counter = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        Stopwatch stopwatch = Stopwatch.createStarted();

        int nThreads = 2;
        for (int i = 0; i < 2; i++) {
            new Thread(CASTest::synchronizedWay).start();
        }

        TimeUnit.MILLISECONDS.sleep(2000);

        stopwatch.stop();

        System.out.println("count = " + count);
        System.out.println("stopwatch.toString() = " + stopwatch.toString());
    }

    /**
     * 传统的方式，count输出的结果小于200，因为count++是非线程安全的，
     */
    private static void traditionalWay() {
        try {
            Thread.sleep(10);
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }

        for (int i = 0; i < 100; i++) {
            count++;
        }
    }

    /**
     * 使用synchronized关键字，虽然保证了线程的安全性，但是效率不是最高的 关键在于，synchronized关键字会让没有得到锁资源的线程进入block状态，
     * 而后在争夺锁资源后进入runnable状态。这其中涉及到操作系统的用户模式和内核模式的转换，代价比较高。
     */
    private static void synchronizedWay() {
        try {
            Thread.sleep(10);
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }

        for (int i = 0; i < 100; i++) {
            synchronized (CASTest.class) {
                count++;
            }
        }
    }

    /**
     * atomic的性能优于synchronized
     */
    private static void atomicIntegerWay() {
        try {
            Thread.sleep(10);
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }

        for (int i = 0; i < 100; i++) {
            counter.incrementAndGet();
        }
    }

    /**************************** 学习笔记(2019年5月16日) ******************************/
//    Atomic操作类的底层正是使用了CAS机制
//    CAS是英文字母：compare and swap的缩写，含义就是比较并替换
//    CAS机制中使用了3个基本操作数，内存地址V，旧的预期值A，要修改的新值B

//    更新一个变量的时候，只有当变量的预期值A和内存地址V当中的实际值相同时，才会将内存地址V对应的值修改为B

//    从思想上来说，synchronized属于悲观锁，CAS属于乐观锁、

//    CAS的缺点：
//        1. CPU开销大，在高并发的情况下，如果线程反复尝试更新一个变量，却又一直更新不成功，循环往复。给CPU带来很大的压力
//        2. 不能保证代码块的原子性，CAS保证的是共享一个变量的原子性操作，而不能保证整个代码块的原子性。如果需要保证3个变量同步进行原子操作，不得不使用synchronized
//        3. ABA问题

//    CAS的ABA问题
//    下面是AtomicInteger当中的常用方法incrementAndGet方法
//     public final int incrementAndGet(){
//            for(;;){
//                int current = get();
//                int next = current + 1;
//                if(compareAndSwap(current, next)){
//                      return next;
//                }
//            }
//     }

//     private volatile int value;
//     public final int get() { return value}

//    这段代码是一个无限循环，也就是CAS的自旋。循环只要做了三件事
//        1. 获取当前值
//        2. 当前值+1，计算出目标值
//        3. 进行CAS操作，如果成功则跳出循环，如果失败重复。

//    其中get方法是核心，这个方法获取的是变量的当前值
//    如果保证获取的值是内存中的最新值？ 使用volatile关键字保证

//    compareAndSwap方法的实现
//    public final boolean compareAndSwap(int expect, int update){
//           return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
//    }
//
//    private static final Unsafe unsafe = Unsafe.getUnsafe();
//    public  static final long valueOffset;

//    static {
//            try {
//                valueOffset = unsafe.objectFileOffset(AtomicInteger.class.getDeclareField("value"));
//            }catch(Exception e){
//            }
//    }
//
//    compareAndSet方法很简单，只有一行代码，这里面涉及到两个比较重要的对象，unsafe和valueOffset

//    什么是unsafe呢？Java不像c或者c++那样可以直接访问底层操作系统，但是JVM为我们留个后门，这个后门就是unsafe。unsafe为我们提供了硬件级别的原子操作

//    valueOffset对象，是通过unsafe.objectFileOffset方法得到的，所代表的是AtomicInteger对象value成员变量在内存中的偏移量。可以简单的理解为内存地址、

//    ABA问题
//    线程1 A -> B  ① A -> B
//    线程2 A -> B   ③ A -> B
//    线程3 更新成A   ② B -> A
//    银行转账会导致错误
}
