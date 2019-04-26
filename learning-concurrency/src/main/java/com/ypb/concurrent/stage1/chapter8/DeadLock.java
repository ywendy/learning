package com.ypb.concurrent.stage1.chapter8;

public class DeadLock {

	private final Object lock = new Object();

	private OtherService otherService;

	public DeadLock(OtherService otherService) {
		this.otherService = otherService;
	}

	public void m1(){
		synchronized (lock) {
			printConsole("m1");
			otherService.s1();
		}
	}

	public void m2(){
		synchronized (lock) {
			printConsole("m2");
		}
	}

	protected void printConsole(String msg) {
		System.out.println(msg);
	}
}
