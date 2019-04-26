package com.ypb.concurrent.stage1.chapter8;

public class DeadLockTest {

	public static void main(String[] args) {
		OtherService service = new OtherService();
		DeadLock lock = new DeadLock(service);

		service.setDeadLock(lock);

		new Thread(() -> {
			while (true) {
				lock.m1();
			}
		}).start();

		new Thread(()-> {
			while (true) {
				service.s2();
			}
		}).start();
	}
}
