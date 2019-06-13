package com.ypb.idempotent.interceptor;

import com.ypb.idempotent.annoation.AccessLimit;
import com.ypb.idempotent.common.RedisService;
import com.ypb.idempotent.exception.ServiceException;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;

/**
 * @ClassName: AccessLimitInterceptor
 * @Description: 防刷的拦截器
 * @author yangpengbing
 * @date 2019-06-12-16:33
 * @version V1.0.0
 *
 */
@Slf4j
public class AccessLimitInterceptor extends AbstractAccessLimitInterceptor {

	@Autowired
	private RedisService redisService;

	@Override
	void check(String key, AccessLimit limit) {
		int maxCount = limit.maxCount();

		Integer count = Integer.valueOf(redisService.get(key));

		if (count >= maxCount) {
			throw new ServiceException("access times over maxCount");
		}
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
}
