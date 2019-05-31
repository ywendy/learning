package com.ypb.timer.v2;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DelayedOperation extends TimerTask {

	public DelayedOperation(int delayMs) {
		super(delayMs);
		log.info("delayMs {}", delayMs);
	}

	@Override
	public void run() {
		System.out.println("biz do"+ System.currentTimeMillis());

		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (InterruptedException e) {
			log.debug(e.getMessage(), e);
		}
	}
}
