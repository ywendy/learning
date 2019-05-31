package com.ypb.concurrent.stage3;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @ClassName: ThreadLocalRandomTest
 * @Description: ThreadLocalRandom 测试
 * @author 杨鹏兵
 * @date 2016年9月26日-上午11:41:33
 * @version V1.0.0
 *
 */
public class ThreadLocalRandomTest {
	public static void main(String[] args) {
		
		Map<String, String> map = new ConcurrentHashMap<>();
		
		/*for(int i = 0; i<100; i++){			
			int nextInt = ThreadLocalRandom.current().nextInt(500000);
			System.out.println(nextInt);
		}*/
	}
}
