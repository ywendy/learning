package com.ypb.concurrent.stage3.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @ClassName: ArrayBlockingQueueTest
 * @Description: 数组阻塞队列测试
 * @author 杨鹏兵
 * @date 2017年7月3日-上午10:44:51
 * @version V1.0.0
 *
 */
public class ArrayBlockingQueueTest {

	public static void main(String[] args) throws Exception {
		int capacity = 1024;
		BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(capacity);
		queue.put(1);
		
		Integer ints = queue.take(); // 先进先出
		System.out.println(ints);
	}
}

/******************************* 学习笔记 **************************************/
//	ArrayBlockingQueue类实现了BlockingQueue接口
//	ArrayBlockingQueue是一个有界的阻塞队列,其内部实现是将对象放到一个数组里,有界也就意味着，它不能够存储无限多数量的元素，它有一个同一时间能够存储元素数量的上限。
//	你可以在对其初始化的时候设定这个上限，但之后就无法对这个上限进行修改(因为它是基于数组实现的，也就具有数组的特性，一旦初始化，大小就无法修改)
//	ArrayBlockingQueue内部以FIFO(先进先出)的顺序对元素进行存储。队列中的头元素在所有元素之中是放入时间最久的那个，而尾元素则是最短时间的那个。
