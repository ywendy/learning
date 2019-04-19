package com.ypb.concurrent.chapter4;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: DaemonThread
 * @Description: 创建守护线程
 * @author yangpengbing
 * @date 2019-04-09-10:03
 * @version V1.0.0
 *
 */
@Slf4j
public class DaemonThread {

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(() -> {
			System.out.println(Thread.currentThread().getName() + " running.");
			try {
				Thread.sleep(10_000);
			} catch (InterruptedException e) {
				log.debug(e.getMessage(), e);
			}

			System.out.println(Thread.currentThread().getName() + " done.");
		}, "DaemonThread1");

		Thread t2 = new Thread(() -> {
			System.out.println(Thread.currentThread().getName() + " running.");
			while (true) {

				try {
					Thread.sleep(10_000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "DaemonThread2");

		Thread t3 = new Thread(() -> {
			Thread temp = new Thread(()->{
				System.out.println(Thread.currentThread().getName() + " running.");

				while (true) {

				}

			},"SubThread");
			// 如果守护线程中创建的用户线程，规则一样，如果用户线程执行完毕，daemon线程将自动退出。
			// 如果用户线程没有执行完毕， daemon线程不会退出。
			temp.setDaemon(Boolean.FALSE);

			temp.start();

			System.out.println(Thread.currentThread().getName() + "is daemon：" + temp.isDaemon());
		}, "DaemonThread3");

		// 此方法必须在start之前设置
		t1.setDaemon(Boolean.TRUE);
		t1.start();

		t2.setDaemon(Boolean.TRUE);
		t2.start();

		t3.setDaemon(Boolean.TRUE);
		t3.start();

		// 如果setDaemon方法在start方法之后设置， 程序抛异常 IllegalThreadStateException
//		t1.setDaemon(Boolean.TRUE);

		Thread.sleep(5_000);
		// main线程不是守护线程
		System.out.println(Thread.currentThread().isDaemon());
		System.out.println(Thread.currentThread().getName());
	}

	/**************************** 学习笔记(2019年4月9日) ******************************/
//	java中线程是一个运用很广泛的知识，我们很有必要了解下守护线程
//	1. 首先我们必须清楚的认识到java线程分为两类，用户线程和守护线程
//	    A. 用户线程：用户线程可以简单的理解为用户定义的线程，当然包含main线程，main线程不是守护线程，
//		B. 守护线程：daemon线程是为我们创建的用户线程提供服务的线程，比如说JVM中的gc，这样的线程有一个非常明显的特征，当用户线程运行结束的时候，
//			       daemon线程将会自动退出。
//	2. daemon线程的特点：
//		A. 守护线程创建的过程中需要先调用setDaemon()方法进程设置，然后在启动线程，否则会报IllegalThreadStateException异常。
//		B. 由于daemon线程的终止条件是当前是否存在用户线程，所以我们不能指派daemon线程来进行一些业务操作。而只能服务用户线程。
//		c. 默认情况下daemon线程的创建的子线程依然是daemon线程。 逻辑在Thread类的init方法中，daemon和priority都是继承父线程的

//	3. daemon守护线程的适用场景
//		A. JVM的GC回收
//		B. 服务间的心跳检查，health check
}
