package com.ypb.concurrent.chapter6;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yangpengbing
 * @version V1.0.0
 * @ClassName: ThreadInterrupt
 * @Description: Thread类的Interrupt方法
 * @date 2019-04-09-15:52
 */
@Slf4j
public class ThreadInterrupt {

	private static final Object MONITOR = new Object();

	public static void main(String[] args) throws InterruptedException {
		/*Thread t1 = new Thread() {

			@Override
			public void run() {
				while (true) {
					synchronized (MONITOR) {
						try {
							MONITOR.wait(10);
						} catch (InterruptedException e) {
							log.debug(e.getMessage(), e);
						}
					}
				}
			}
		};

		t1.start();
		Thread.sleep(10_000);
		System.out.println(t1.isInterrupted());
		t1.interrupt();
		System.out.println(t1.isInterrupted());*/

		Thread t = new Thread() {

			@Override
			public void run() {
				while (true) {

				}
			}
		};

		t.start();
		Thread main = Thread.currentThread();
		Thread t2 = new Thread() {

			@Override
			public void run() {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					log.debug(e.getMessage(), e);
				}

				main.interrupt();
				System.out.println("interrupt.");
			}
		};

		t2.start();

		t.join();
	}

	/**************************** 学习笔记(2019年4月9日) ******************************/
//	https://www.cnblogs.com/carmanloneliness/p/3516405.html
//	http://www.cnblogs.com/w-wfy/p/6415005.html

//	interrupt status(中断状态)。中断机制就是围绕这个字段来工作的。在java源码中代表中断的状态的字段是private volatile Interruptible blocker
//	对Interruptible这个类不需要深入分析，对于blocker变量有下面几个操作：
//	    1. 默认blocker = null ①
//		2. 调用方法interrupt0(); 将会导致该线程的中断状态将被设置 ②
//		3. 再次调用interrupt0(); 将会导致其中断状态将被清除 ③

//	1. public void interrupt();
//		中断线程，如果线程在调用Object类的wait()，wait(long)或者wait(long, int)方法，或者该类的join()，join(long)，join(long, int)，sleep(long)或者sleep(long, int)方法
//	    过程中受阻，则其中断状态被清除，还将收到一个InterruptedException.

//	2. public static boolean interrupted();
//		测试当前线程是否已经中断，线程的中断状态由该方法清除，线程中断被忽略，因为在中断时不处于活动状态的线程将由此返回false的方法反映出来。
//	    如果当前线程已经中断，返回true，否则返回false

//	3. public boolean isInterrupted();
//		测试线程是否已经中断，线程的中断状态不受该方法的影响，线程中断被忽略，因为在中断时不处于活动状态的线程由此返回false的方法反映出来。
//		如果线程当前线程已经中断，返回true，否则返回false

//	interrupted()和isInterrupted()两个方法的相同的和不同点
//	源码如下：
//	public static boolean interrupted(){
//		return currentThread().isInterrupted(true);
//	}

//	public boolean isInterrupted(){
//		return isInterrupted(false);
//	}

//	private native boolean isInterrupted(boolean ClearInterrupted);
//  相同点都是判断线程的interrupt status是否被设置，若被设置返回true，否则返回false。区别主要有两点：
//	    1. 前者是静态方法，调用者current Thread。而后者是普通方法，调用者是this。
//		2. 它们其实都调用了java中的native方法isInterrupted(boolean ClearInterrupted); 不同的是前者传递是true，后置是false。
//			含义就是：前者将清除线程的interrupt state, 调用后者线程的interrupt status不受影响。






















}
