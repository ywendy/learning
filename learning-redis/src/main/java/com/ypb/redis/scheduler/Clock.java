package com.ypb.redis.scheduler;

import java.util.Calendar;

public interface Clock {

	Calendar now();
}
