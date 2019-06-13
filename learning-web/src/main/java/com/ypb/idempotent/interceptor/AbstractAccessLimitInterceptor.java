package com.ypb.idempotent.interceptor;

import com.ypb.idempotent.annoation.AccessLimit;
import com.ypb.idempotent.utils.WebUtils;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @ClassName: AbstractAccessLimitInterceptor
 * @Description: 防刷的抽象类
 * @author yangpengbing
 * @date 2019-06-13-16:11
 * @version V1.0.0
 *
 */
public abstract class AbstractAccessLimitInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!(handler instanceof HandlerMethod)) {
			return Boolean.TRUE;
		}

		HandlerMethod hm = (HandlerMethod) handler;
		AccessLimit limit = hm.getMethod().getAnnotation(AccessLimit.class);

		if (Objects.nonNull(limit)) {
			check(initKey(request), limit);
		}

		return Boolean.TRUE;
	}

	String initKey(HttpServletRequest request) {
		String ip = WebUtils.getIpAddress(request);

		StringBuilder builder = new StringBuilder();
		builder.append("access:limit:").append(ip).append(":").append(request.getRequestURI());

		return builder.toString();
	}

	/**
	 * 校验，校验失败，抛异常
	 * @param key
	 * @param limit
	 */
	abstract void check(String key, AccessLimit limit);
}
