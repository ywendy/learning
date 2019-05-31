package com.ypb.concurrent.stage3.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName: AtomicTest
 * @Description: 类的小工具包，支持在单个变量上解除锁的线程安全编程。
 * @author 杨鹏兵
 * @date 2016年9月19日-上午11:40:46
 * @version V1.0.0
 *
 */
public class AtomicTest {

	private static int count = 1;
	
	public static void main(String[] args) {
		AtomicInteger ai = new AtomicInteger(count);
		for(int i = 0; i<12; i++){			
			System.out.println(ai.getAndIncrement());
		}
	}
}
