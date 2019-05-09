package com.ypb.redis.scheduler.impl;

import com.ypb.redis.scheduler.Clock;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;
import org.joda.time.format.DateTimeFormat;

public class StandardClock implements Clock {

	@Override
	public Calendar now() {
		return Calendar.getInstance();
	}
}
