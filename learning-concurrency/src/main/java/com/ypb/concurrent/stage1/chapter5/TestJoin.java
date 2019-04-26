package com.ypb.concurrent.stage1.chapter5;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yangpengbing
 * @version V1.0.0
 * @ClassName: TestJoin
 * @Description: 测试join的方法
 * @date 2019-04-09-12:18
 */
@Slf4j
public class TestJoin implements Runnable {

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new TestJoin());

		long start = System.currentTimeMillis();
		new Thread(() -> show(t1)).start();

		System.out.println("11111>>>>");

		t1.start();
		t1.join(5000);

		System.out.println(System.currentTimeMillis() - start);
		System.out.println("Main finish.");
	}

	private static void show(Thread t1) {
		// 说明join方法持有的对象锁是t1对象本身 用t1和t1.getName试验
		synchronized (t1){
			try {
				// sleep是不释放对象锁的
				Thread.sleep(10_000);
			} catch (InterruptedException e) {
				log.debug(e.getMessage(), e);
			}

			System.out.println(Thread.currentThread().getName() + "111");
		}
	}

	@Override
	public void run() {
//		synchronized (Thread.currentThread()){
			System.out.println("TestJoin running.");
			for (int i = 0; i < 5; i++) {
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					log.debug(e.getMessage(), e);
				}

				System.out.println("sleep: " + i);
			}

			System.out.println("TestJoin finish.");
//		}
	}

	/**************************** 学习笔记(2019年4月9日) ******************************/
//	join()方法的作用是等待这个线程结束，也就是说，t.join()方法阻塞调用此方法的线程，直到线程t执行完毕，此线程才继续。
}
