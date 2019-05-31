package com.ypb.concurrent.stage3;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @ClassName: CycliBarrierTest
 * @Author: 杨鹏兵
 * @Date: 2016年7月4日 下午3:29:45
 * @Version: v1.0.0
 */
public class CycliBarrierTest {
	
	private static final Integer NUM = 4;
	
	/**
	 * 需求：今天晚上我们哥们4个去Happy。就互相通知了一下：晚上八点准时到xx酒吧门前集合，不见不散！。
	 * 		有个哥们住的近，早早就到了。有的事务繁忙，刚好踩点到了。无论怎样，先来的都不能独自行动，只能等待所有人
	 * @Method: main
	 * @Anthor: 杨鹏兵
	 * @param args
	 * @throws BrokenBarrierException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws Exception {
		ExecutorService exec = Executors.newCachedThreadPool();
		final Random random = new Random();
		
		final CyclicBarrier barrier = new CyclicBarrier(NUM, new Runnable(){

			@Override
			public void run() {	
				System.out.println("大家都到期了，开始去happy去");
			}
			
		});
		
		for(int i = 1; i<=NUM; i++){
			exec.execute(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(random.nextInt(1000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					System.out.println(Thread.currentThread().getName()+"到了，其他哥们呢");
					
					try {
						barrier.await(); // 等待
					} catch (InterruptedException | BrokenBarrierException e) {
						e.printStackTrace();
					} 
				}				
			});
		}
			
		exec.shutdown();
	}

	/******************************* 学习笔记 **************************************/
//	CyclicBarrier
//    我们先来学习一下JDK1.5 API中关于这个类的详细介绍：
//    “一个同步辅助类，它允许一组线程互相等待，直到到达某个公共屏障点 (common barrier point)。在涉及一组固定大小的线程的程序中，这些线程必须不时地互相等待，此时 CyclicBarrier 很有用。因为该 barrier 在释放等待线程后可以重用，所以称它为循环 的 barrier。
//    CyclicBarrier 支持一个可选的 Runnable 命令，在一组线程中的最后一个线程到达之后（但在释放所有线程之前），该命令只在每个屏障点运行一次。若在继续所有参与线程之前更新共享状态，此屏障操作 很有用。

//    我们在学习CountDownLatch的时候就提到了CyclicBarrier。两者究竟有什么联系呢？引用[JCIP]中的描述“The key difference is that with a barrier, all the threads must come together at a barrier point at the same time in order to proceed. Latches are for waiting for events; barriers are for waiting for other threads。CyclicBarrier等待所有的线程一起完成后再执行某个动作。这个功能CountDownLatch也同样可以实现。但是CountDownLatch更多时候是在等待某个事件的发生。在CyclicBarrier中，所有的线程调用await方法，等待其他线程都执行完。
	
//	总结：CyclicBarrier就是一个栅栏，等待所有线程到达后再执行相关的操作。barrier 在释放等待线程后可以重用。 
}
