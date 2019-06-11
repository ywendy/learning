package com.ypb.timer.v2;

import java.util.concurrent.TimeUnit;

public class Time {

	static Long getHiresClockMs() {
		return TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
	}
}
