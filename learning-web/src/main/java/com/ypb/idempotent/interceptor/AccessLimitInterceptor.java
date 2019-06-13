package com.ypb.idempotent.interceptor;

import com.google.common.util.concurrent.RateLimiter;
import com.ypb.idempotent.annoation.AccessLimit;
import com.ypb.idempotent.common.RedisService;
import com.ypb.idempotent.exception.ServiceException;
import com.ypb.idempotent.utils.WebUtils;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @ClassName: AccessLimitInterceptor
 * @Description: 防刷的拦截器
 * @author yangpengbing
 * @date 2019-06-12-16:33
 * @version V1.0.0
 *
 */
@Slf4j
public class AccessLimitInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private RedisService redisService;
	private AtomicInteger ai = new AtomicInteger();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!(handler instanceof HandlerMethod)) {
			return Boolean.TRUE;
		}

		HandlerMethod hm = (HandlerMethod) handler;
		AccessLimit limit = hm.getMethod().getAnnotation(AccessLimit.class);

		if (Objects.nonNull(limit)) {
			check(limit, request);
		}

		return Boolean.TRUE;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

		if (!(handler instanceof HandlerMethod)) {
			return;
		}

		HandlerMethod hm = (HandlerMethod) handler;
		AccessLimit limit = hm.getMethod().getAnnotation(AccessLimit.class);

		if (Objects.isNull(limit)) {
			return;
		}

		String key = initKey(request);

		redisService.incr(key);
		redisService.pExpire(key, limit.unit().toMillis(limit.times()));
	}

	private void check(AccessLimit limit) {
		RateLimiter limiter = RateLimiter.create(limit.maxCount(), limit.times(), limit.unit());

		if (!limiter.tryAcquire()) {
			throw new ServiceException("access times over maxCount");
		}
	}

	private void check(AccessLimit limit, HttpServletRequest request) {

		int maxCount = limit.maxCount();
		String key = initKey(request);

		Integer count = Integer.valueOf(redisService.get(key));

		if (count >= maxCount) {
			throw new ServiceException("access times over maxCount");
		}
	}

	private String initKey(HttpServletRequest request) {
		String ip = WebUtils.getIpAddress(request);

		StringBuilder builder = new StringBuilder();
		builder.append("access:limit:").append(ip).append(":").append(request.getRequestURI());

		return builder.toString();
	}
}
