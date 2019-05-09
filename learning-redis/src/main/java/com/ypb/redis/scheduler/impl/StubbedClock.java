package com.ypb.redis.scheduler.impl;

import com.ypb.redis.scheduler.Clock;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;
import org.joda.time.format.DateTimeFormat;

public class StubbedClock implements Clock {

	private Calendar now = new GregorianCalendar();

	@Override
	public Calendar now() {
		return now;
	}

	public void is(String dateTimeStr) {
		Calendar calendar = Calendar.getInstance();

		Date date = DateTimeFormat.forPattern("yyyyMMdd HH:mm").parseDateTime(dateTimeStr).toDate();
		calendar.setTime(date);

		stubTime(calendar);
	}

	public void fastForward(int period, TimeUnit unit) {
		stubTime(in(period, unit));
	}

	public Calendar in(int period, TimeUnit unit) {
		long timeInMillis = now.getTimeInMillis();
		timeInMillis = timeInMillis + (unit.toMillis(period));

		return forMillis(timeInMillis);
	}

	private Calendar forMillis(long timeInMillis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeInMillis);

		return calendar;
	}

	private void stubTime(Calendar calendar) {
		now = calendar;
	}
}
