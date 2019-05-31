package com.ypb.concurrent.stage3.blockingqueue;

/**
 * @ClassName: SynchronousQueueTest
 * @Description: 同步阻塞队列测试
 * @author 杨鹏兵
 * @date 2017年7月3日-下午3:25:02
 * @version V1.0.0
 *
 */
public class SynchronousQueueTest {

}

/******************************* 学习笔记 **************************************/
//	SynchronousQueue类实现了BlockingQueue接口
//	SynchronousQueue是一个特殊的队列，它的内部同时能够容纳单个元素，如果该队列已有一个元素，试图向队列中插入一个新元素的线程将会阻塞，直到另一个线程将该元素从队列中抽走。
//	同样，如果队列为空，试图向队列中抽取一个元素的线程将会阻塞，直到另一个线程向队列中插入一条新的元素。
//	据此，把这个类称作一个队列显然是夸大其词了。它更多像是一个汇合点