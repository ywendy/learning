package com.ypb.concurrent.stage2.chapter18;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DisplayStringClientRunnable extends AbstractClientRunnable {


	public DisplayStringClientRunnable(ActiveObject activeObject, ResultQueue queue) {
		super(activeObject, queue);
	}

	@Override
	protected void doRun() {
		try {
			while (true) {
				String value = (String) queue.take().getResultValue();
				String text = Thread.currentThread().getName() + ": --> " + value;

				slowly(15);

				activeObject.displayString(text);
			}
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}
	}
}
