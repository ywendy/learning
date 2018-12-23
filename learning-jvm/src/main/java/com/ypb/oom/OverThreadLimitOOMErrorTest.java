package com.ypb.oom;

import java.util.Map;
import java.util.Random;

/**
 * @className OverThreadLimitOOMErrorTest
 * @description 垃圾回收超时导致的内存溢出(-Xmx32m -Xms32m -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -verbose:gc -Xloggc:E:/log.gc -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=D:/dump/dump.hprof)
 * @author yangpengbing
 * @date 22:31 2018/12/23
 * @version 1.0.0
 */
public class OverThreadLimitOOMErrorTest {

    public static void main(String[] args) {
        Map map = System.getProperties();
        Random r = new Random();
        while (map.size() >= 0) {
            int i = r.nextInt();
            map.put(i, "ypb" + i);
        }
    }
}
