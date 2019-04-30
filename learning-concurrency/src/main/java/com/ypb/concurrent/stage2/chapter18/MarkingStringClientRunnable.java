package com.ypb.concurrent.stage2.chapter18;

import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

@Slf4j
public class MarkingStringClientRunnable extends AbstractClientRunnable {

	public MarkingStringClientRunnable(ActiveObject activeObject, ResultQueue queue) {
		super(activeObject, queue);
	}

	@Override
	protected void doRun() {
		try {
			for (int i = 0; true; i++) {
				char fillChar = initFillChar(BigDecimal.ONE.intValue());

				Result<String> result = markingString(Math.incrementExact(i), fillChar);
				String value = result.getResultValue();

				slowly(10);

				printConsole(value);

				queue.put(result);
			}
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}
	}

	private void printConsole(String value) {
		System.out.println(Thread.currentThread().getName() + ": value = " + value);
	}

	private char initFillChar(int index) {
		return RandomStringUtils.randomAlphanumeric(index).charAt(BigDecimal.ZERO.intValue());
	}

	private Result<String> markingString(int count, char fillChar) {
		return activeObject.makeString(count, fillChar);
	}
}


