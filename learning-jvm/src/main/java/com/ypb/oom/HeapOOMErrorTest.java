package com.ypb.oom;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @className HeapOOMErrorTest
 * @description java堆内存内存溢出测试(-Xmx20m -Xms20m -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -verbose:gc -XX:+PrintGCDateStamps -Xloggc:E:/gc.log)
 * @author yangpengbing
 * @date 22:50 2018/12/20
 * @version 1.0.0
 */
public class HeapOOMErrorTest {

    public static final int m = 1024 * 1025;

    public static void main(String[] args) {
        List<byte[]> bytes = Lists.newArrayList();
        int i = 0;
        while (true) {
            bytes.add(new byte[5 * m]);
            System.out.println("count is: " + (++i));

            if (i > Short.MAX_VALUE) {
                break;
            }
        }
    }
}
