package com.ypb.concurrent.stage2.chapter18;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public abstract class AbstractClientRunnable implements Runnable {

	protected final ActiveObject activeObject;
	protected final ResultQueue queue;
	private static final Random random = new Random(System.currentTimeMillis());

	public AbstractClientRunnable(ActiveObject activeObject, ResultQueue queue) {
		this.activeObject = activeObject;
		this.queue = queue;
	}

	protected void slowly(int mills) throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(random.nextInt(mills));
	}

	@Override
	public void run() {
		doRun();
	}

	protected abstract void doRun();
}
