package com.ypb.oom;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Map;
import java.util.Random;

/**
 * @author yangpengbing
 * @version 1.0.0
 * @className OverThreadLimitOOMErrorTest
 * @description 垃圾回收超时导致的内存溢出(- Xmx32m - Xms32m - XX : + UseConcMarkSweepGC - XX : + PrintGCDetails - XX : + PrintGCDateStamps
 *- verbose : gc - Xloggc : E : / gc.log - XX : + HeapDumpOnOutOfMemoryError - XX : HeapDumpPath = D : / dump / dump.hprof)
 * https://blog.csdn.net/renfufei/article/details/77585294
 * @date 22:31 2018/12/23
 */
public class OverThreadLimitOOMErrorTest {

	public static void main(String[] args) throws Exception {
		RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
		System.out.println(bean.getInputArguments());

		Map map = System.getProperties();
		Random r = new Random();
		while (map.size() >= 0) {
			map.put(r.nextInt(), "ypb");
		}
	}
}
