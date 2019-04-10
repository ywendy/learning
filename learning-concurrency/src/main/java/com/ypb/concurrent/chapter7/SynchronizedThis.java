package com.ypb.concurrent.chapter7;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: SynchronizedThis
 * @Description: synchronized的对象监听器this
 * @author yangpengbing
 * @date 2019-04-10-16:53
 * @version V1.0.0
 *
 */
@Slf4j
public class SynchronizedThis {

	public static void main(String[] args) {
		ThisLock lock = new ThisLock();

		new Thread(() -> lock.m1(), "T1").start();
		new Thread(() -> lock.m2(), "T2").start();
	}

	@Slf4j
	private static class ThisLock {

		private final Object LOCK = new Object();

		public void m1(){
			synchronized (LOCK) {
				System.out.println(Thread.currentThread().getName());
//				sleep();

				try {
					LOCK.wait(10_000);
					System.out.println(Thread.currentThread().getName() + " done.");
				} catch (InterruptedException e) {
					log.debug(e.getMessage(), e);
				}
			}
		}

		public void m2(){
			synchronized (LOCK) {
				System.out.println(Thread.currentThread().getName());
				sleep();
			}
		}

		private void sleep() {
			try {
				Thread.sleep(10_000);
			} catch (InterruptedException e) {
				log.debug(e.getMessage(), e);
			}
		}
	}

	/**************************** 学习笔记(2019年4月10日) ******************************/
//	wait和sleep方法，sleep方法是不释放对象锁的，wait方法释放对象锁
}
