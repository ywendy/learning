/**
 * @ClassName: package-info
 * @Description: 阻塞队列(http://blog.csdn.net/defonds/article/details/44021605)
 * @author 杨鹏兵
 * @date 2017年6月30日-下午5:41:10
 * @version V1.0.0
 *
 */
package com.ypb.concurrent.stage3.blockingqueue;

/******************************* 学习笔记 **************************************/
//阻塞队列BlockingQueue 
//	java.util.concurrent包里的BlockingQueue接口表示一个线程安全放入和提取实例的队列，
//BlockingQueue用法
//	BlockingQueue通常用于一个线程生产对象，而另一个线程消费对象这些对象的场景 
//	一个线程往里面放，另一个线程从里面取的一个BlockingQueue，一个线程将会持续生产新对象并将其插入到队列中，直到队列到达它所容纳的临界点，也就是说，它是有限的，
//	如果该阻塞队列到达了其临界点，负责生产的线程将会在往里面边插入新对象时发生阻塞，它会一直处于阻塞之中，直到负责消费的线程从中拿走一个对象。
//	负责消费的线程将会一直从该阻塞队列中拿对象，如果消费线程尝试去从一个空的队列中提取对象的话，这个消费线程将会处于阻塞之中，知道一个生产线程把一个对象丢进队列。
//BlockingQueue方法
//	BlockingQueue具有4组不同的方法插入，移除已经对队列中元素进行检查，如果请求的操作不能得到立即执行的话，每个不同的方法表现也不同，这些方法如下
//			    抛异常		特定值	    阻塞	超时
//	插入		add(o)		offer(o)	put(o)	offer(o, timeout, timeunit)
//	移除		remove(o)	poll(o)     take()	poll(timeout, timeunit)
//	检查		element(o)	peek(o)
//	四组不同的行为方式解释
//		1.抛异常:如果试图的操作无法立即执行，抛异常
//		2.特定值:如果试图的操作无法立即执行，返回一个特定值(常常是true/false)
//		3.阻塞: 如果试图的操作无法立即执行，该方法调用将会发生阻塞，知道能够执行。
//		4.超时: 如果试图的操作无法立即执行，该方法调用将会发生阻塞，直到能够执行，但等待时间不会超过给定值，返回一个特定值以告知该操作是否成功(典型的是true/false)
//	无法向一个BlockingQueue中插入null。如果试图插入null。BlockingQueue将会抛异常NPE(NullPoinerException)
//	可以访问到BlockingQueue中的所有元素，而不仅仅是开始和结束的元素，比如说，你将一个元素放入队列之中以等待处理，当你的应用想要将其取消掉，那么你可以调用诸如remove(o)方法来将队列之中等待的元素进行移除。
//	但是这么干的效率并不高(基于队列的数据结构，获取除开始或结束位置的其他元素的效率不会太高)，因此你尽量不要用这一类方法，除非你确实不得不这么做。
//BlockingQueue实现
//	BlockingQueue是一个接口，你需要使用它的实现之一来使用BlockingQueue，java.util.concurrent具有以下BlockingQueue接口实现(JAVA6)
//	ArrayBlockingQueue(数组阻塞队列)
//	DelayQueue(延迟队列)
//	LinkedBlockingQueue(链表阻塞队列)
//	PriorityBlockQueue(具有优先级的阻塞队列)
//	SynchronousQueue(同步队列)