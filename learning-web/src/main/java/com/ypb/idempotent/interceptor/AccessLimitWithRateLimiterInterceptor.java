package com.ypb.idempotent.interceptor;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.RateLimiter;
import com.ypb.idempotent.annoation.AccessLimit;
import com.ypb.idempotent.exception.ServiceException;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yangpengbing
 * @version V1.0.0
 * @ClassName: AccessLimitWithRateLimiterInterceptor
 * @Description: 基于guava中的rateLimiter实现的防刷
 * @date 2019-06-13-16:26
 */
@Slf4j
public class AccessLimitWithRateLimiterInterceptor extends AbstractAccessLimitInterceptor {

	private static final String KEY = "rateLimiter";
	private static Cache<String, RateLimiter> cache = null;
	static {
		int capacity = BigDecimal.ONE.intValue();

		cache = CacheBuilder.newBuilder().maximumSize(capacity).initialCapacity(capacity).build();
	}

	@Override
	void check(String key, AccessLimit limit) {
		RateLimiter limiter = getRateLimiterByCache(limit);

		if (limiter == null || !limiter.tryAcquire()) {
			throw new ServiceException("access times over maxCount");
		}
	}

	private RateLimiter getRateLimiterByCache(AccessLimit limit) {
		RateLimiter limiter = null;
		try {
			limiter = cache.get(KEY, () -> create(limit));
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}

		return limiter;
	}

	private RateLimiter create(AccessLimit limit) {
		return RateLimiter.create(limit.maxCount(), limit.times(), limit.unit());
	}
}
