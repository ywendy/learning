package com.ypb.jvm;

import lombok.Data;

/**
 * @author yangpengbing
 * @version V1.0.0
 * @ClassName: EdenTest
 * @Description: 验证对象有限创建在eden区域(- Xmx10M - Xms10M - XX : + UseConcMarkSweepGC - verbose - XX : + PrintGCDetails)
 * @date 2018/12/10-17:24
 */
public class EdenTest {

	public static void main(String[] args) {
		for (int i = 0, lenth = 1000; i < lenth; i++) {
			EdenEntity entity = new EdenEntity();
		}
	}

	@Data
	private static class EdenEntity {

		private int age;
	}

	/**************************** 学习笔记(2018年12月10日) ******************************/
//	Heap
//	par new generation   total 3072K, used 2060K [0x00000000ff600000, 0x00000000ff950000, 0x00000000ff950000)
//	eden space 2752K,  74% used [0x00000000ff600000, 0x00000000ff803028, 0x00000000ff8b0000)
//	from space 320K,   0% used [0x00000000ff8b0000, 0x00000000ff8b0000, 0x00000000ff900000)
//	to   space 320K,   0% used [0x00000000ff900000, 0x00000000ff900000, 0x00000000ff950000)
//	concurrent mark-sweep generation total 6848K, used 0K [0x00000000ff950000, 0x0000000100000000, 0x0000000100000000)
//	Metaspace       used 3470K, capacity 4500K, committed 4864K, reserved 1056768K
//	class space    used 381K, capacity 388K, committed 512K, reserved 1048576K
}
