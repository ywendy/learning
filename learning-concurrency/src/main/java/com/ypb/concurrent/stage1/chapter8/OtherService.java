package com.ypb.concurrent.stage1.chapter8;

import lombok.Setter;

public class OtherService {

	private final Object lock = new Object();

	@Setter
	private DeadLock deadLock;

	public void s1() {
		synchronized (lock) {
			printConsole("s1");
		}
	}

	public void s2(){
		synchronized (lock) {
			printConsole("m2");
			deadLock.m2();
		}
	}

	private void printConsole(String msg) {
		deadLock.printConsole(msg);
	}
}
