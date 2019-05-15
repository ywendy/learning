package com.ypb.concurrent.stage3.chapter1;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.assertEquals;

public class AtomicLongTest {
    
    @Test
    public void testCreate(){
        AtomicLong al = new AtomicLong(100L);

        assertEquals(100L, al.get());
    }

    /**************************** 学习笔记(2019年5月14日) ******************************/
//    AtomicLong和AtomicInteger的区别，主要是AtomicLong的类中多了一个VMSupportLockCAS的变量，
//    其主要是JVM使用的, 作用是判断long类型是否支持CAS

//    内存和cpu通讯是依赖于地址总线，数据总线，和控制总线，
//    而long类型占用的8个字节，64位，cpu的总线是32，数据需要分两次传输，不保证原子性的， 高位 32 地位 32

//    如果不支持，cpu回到数据总线添加锁。保证原子性
}
