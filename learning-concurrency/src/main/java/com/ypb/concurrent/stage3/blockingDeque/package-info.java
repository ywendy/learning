/**
 * @ClassName: package-info
 * @Description: 阻塞双端队列BlockingDeque
 * @author 杨鹏兵
 * @date 2017年7月3日-下午3:36:12
 * @version V1.0.0
 *
 */
package com.ypb.concurrent.stage3.blockingDeque;

/******************************* 学习笔记 **************************************/
//	java.util.concurrent包里的BlockingDeque接口表示一个线程安全放入和提取实例的双端队列，
//	BlockingDeque类是一个双端队列，在不能够插入元素时，它将阻塞住试图插入元素的线程，在不能提取元素时，它将阻塞住试图提取线程
//	deque(双端队列)是Double Ended Queue的缩写，因此双端队列是一个你可以从任意一端插入或者抽取元素的队列。
//	BlockingDeque的使用
//	在线程既是一个队列的生存者，又是这个队列的消费者的时候，可以使用到BlockingDeque，如果生产者线程需要在队列的两端都可以插入数据，消费者线程需要在队列的两端都可以移除元素这个时候也可以使用BlockingDeque
//	BlockingDeque图解：
//		一个线程BlockingDeque线程在双端队列的两端都可以插入和提取元素
//		一个线程生成元素，并把他们插入到队列的任意一段，如果双端队列已满，插入线程被阻塞，直到一个移除线程从该队列中移除一个元素，如果双端队列为空，移除线程被阻塞，直到一个插入线程向队列中插入了一个元素
//	BlockingDeque的方法
//	BlockingDeque具有4组不同的方法插入，移除以及对双端队列中的元素进行检查，如果请求的操作不能立即执行的话，每个方法的表现也不一样。
//				    抛异常			特定值			阻塞			超时
//		队列头插入	addFirst(o)		offerFirst(o)	putFirst(o)		offFirst(o, timeout, timeunit)
//		队列尾插入	addLast(o)		offerLast(o)	putLast(o)		offLast(o, timeout, timeunit)
//		队列头移除	removeFirst(o)  pollFirst(o)	takeFirst(o)	pollFirst(o, timeout, timeunit)
//		队列尾移除	removeLast(o)	pollLast(o)		takeLast(o)		pollLast(o, timeout, timeunit)
//		队列头检查	getFirst(o)		peekFirst(o)
//		队列尾检查	getLast(o)		peekLast(o)

//		四组不同的行为方式解释：
//		
//		抛异常：如果试图的操作无法立即执行，抛一个异常。
//		特定值：如果试图的操作无法立即执行，返回一个特定的值(常常是 true / false)。
//		阻塞：如果试图的操作无法立即执行，该方法调用将会发生阻塞，直到能够执行。
//		超时：如果试图的操作无法立即执行，该方法调用将会发生阻塞，直到能够执行，但等待时间不会超过给定值。返回一个特定值以告知该操作是否成功(典型的是 true / false)。