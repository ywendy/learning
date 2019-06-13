package com.ypb.idempotent.interceptor;

import com.ypb.idempotent.annoation.AccessLimit;
import com.ypb.idempotent.common.RedisService;
import com.ypb.idempotent.exception.ServiceException;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yangpengbing
 * @version V1.0.0
 * @ClassName: AccessLimitInterceptorExt
 * @Description: 使用lua脚本实现
 * @date 2019-06-13-16:06
 */
public class AccessLimitWithLuaScriptInterceptor extends AbstractAccessLimitInterceptor {

	@Autowired
	private RedisService redisService;

	@Override
	void check(String key, AccessLimit limit) {
		int maxCount = limit.maxCount();
		long millis = limit.unit().toMillis(limit.times());

		Long acquire = redisService.acquire(key, maxCount, millis);

		if (acquire <= BigDecimal.ZERO.longValue()) {
			throw new ServiceException("access times over maxCount");
		}
	}
}
