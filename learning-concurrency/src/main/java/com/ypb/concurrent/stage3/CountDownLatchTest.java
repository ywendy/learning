package com.ypb.concurrent.stage3;

import java.util.concurrent.CountDownLatch;

/**
 * java.util.concurrent详解-latch
 * @ClassName: CountDownLatchTest
 * @Author: 杨鹏兵
 * @Date: 2016年7月4日 下午2:18:31
 * @Version: v1.0.0
 */
public class CountDownLatchTest {

	private static final Integer NUM = 10;
	
	/**
	 * 需求：我们要打印1-100，在输出"ok". 1-100的打印顺序不要求统一，只需要保证"ok"是最后面出现即可
	 * 解决方案： 我们定义一个CountDownLatch，然后开10个线程分别打印(n-1)*10+1到(n-1)*10+10.
	 * 			主线程中调用await方法等待所有线程的执行完毕，每个线程执行完毕后都调用countDown方法，
	 * 			最后在await方法返回后打印"ok"
	 * @Method: main
	 * @Anthor: 杨鹏兵
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		CountDownLatch doneSignal = new CountDownLatch(NUM); //结束执行信号
		CountDownLatch startSignal = new CountDownLatch(1); //开始执行信号
		
		//开启NUM个线程
		for(int i = 1; i<=NUM; i++){
			new Thread(new Worker(i, startSignal, doneSignal)).start();
		}
		
		System.out.println("begin--------------");
		startSignal.countDown(); //发布开始执行信号
		doneSignal.await(); //等待所有的线程执行完毕
		System.out.println("Ok");
	}
	
	static class Worker implements Runnable{
		private final CountDownLatch startSignal;
		private final CountDownLatch doneSignal;
		private int beginIndex;
		
		public Worker(int beginIndex, CountDownLatch startSignal, CountDownLatch doneSignal) {
			this.beginIndex = beginIndex;
			this.startSignal = startSignal;
			this.doneSignal = doneSignal;
		}
		
		@Override
		public void run() {
			try {
				startSignal.await(); //等待开始执行信号的发布
				beginIndex = (beginIndex - 1) * 10 + 1;
				//打印
				for(int i = beginIndex; i<beginIndex+10; i++){
					System.out.println(i);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally{
				doneSignal.countDown();
			}
		}
		
	}
	
	/******************************* 学习笔记 **************************************/
//	CountDownLatch
//    我们先来学习一下JDK1.5 API中关于这个类的详细介绍：
//	      一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。 
//	     用给定的计数 初始化 CountDownLatch。由于调用了 countDown() 方法，所以在当前计数到达零之前，await 方法会一直受阻塞。
//	      之后，会释放所有等待的线程，await 的所有后续调用都将立即返回。这种现象只出现一次——计数无法被重置。
//	     如果需要重置计数，请考虑使用 CyclicBarrier。”
//
//    这就是说，CountDownLatch可以用来管理一组相关的线程执行，只需在主线程中调用CountDownLatch 的await方法（一直阻塞），
//	     让各个线程调用countDown方法。当所有的线程都只需完countDown了，await也顺利返回，不再阻塞了。
//	      在这样情况下尤其适用：将一个任务分成若干线程执行，等到所有线程执行完，再进行汇总处理。
	
//	http://www.tuicool.com/articles/mQnAfq
	
//	CountDownLatch简介：
//		CountDownLatch是一个同步辅助类,在完成一组正在其他线程中执行的操作之前,它允许一个或者多个线程一直等待.
//		CountDownLatch和CyclicBarrier的区别
//			CountDownLatch的作用是允许1或者N个线程等待其他线程完成执行；而CyclicBarrier则是允许N个线程相互等待。
//			CountDownLatch的计数器无法被重置；CyclicBarrier的计数器可以被重置后使用，因此它被成为是循环的barrier。
			
//		CountDownLatch成员函数列表：
//			CountDownLatch(int count); //构造一个用给定计数初始化的CountDownLatch。
//			void await(); // 当前线程在锁存器倒计数至零之前一直等待，除非线程被中断。
//			boolean await(long timeout, TimeUnit unit); // 使当前线程在锁存器倒计数至零之前一直等待，除非线程被中断或者超出了指定的等待时间。
//			void countDown(); // 递减锁存器的计数，如果计数到达零，则释放所有的等待的线程。
//			long getCount; // 返回当前计数
//			String toString(); //返回标识此锁存器及其状态的字符串
	
//	总结：
//		CountDownLatch对于管理一组相关线程非常有用，上述实例代码中就形象的描述了两种使用情况，第一中计数器是1代表了两种状态，开关，第二种是计数器NUM，代表了等待NUM个操作完成。
		
}
