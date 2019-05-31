package com.ypb.concurrent.stage3.blockingqueue;

/**
 * @ClassName: LinkedBlockingQueueTest
 * @Description: 链表阻塞队列测试
 * @author 杨鹏兵
 * @date 2017年7月3日-下午3:04:32
 * @version V1.0.0
 *
 */
public class LinkedBlockingQueueTest {

}

/******************************* 学习笔记 **************************************/
//	LinkedBlockingQueue类实现了BlockingQueue接口。
//	LinkedBlockingQueue内部以一个链式结构(链接节点)对其元素进行存储，如果需要的话，这一链式结构可以选择一个上限，如果没有定义上限，将使用Integer.MAX_VALUE作为上限。
//	LinkedBlockingQueue内部以FIFO(先进先出)的顺序对元素进行存储，队列中的头元素在所有元素之中是放入时间最久的那个，而尾元素是最短的那个
