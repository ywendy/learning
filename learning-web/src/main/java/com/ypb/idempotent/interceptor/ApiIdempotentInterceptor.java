package com.ypb.idempotent.interceptor;

import com.ypb.idempotent.annoation.ApiIdempotent;
import com.ypb.idempotent.service.TokenService;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @ClassName: ApiIdempotentInterceptor
 * @Description: 接口幂等性拦截器
 * @author yangpengbing
 * @date 2019-06-12-14:37
 * @version V1.0.0
 *
 */
public class ApiIdempotentInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private TokenService tokenService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!(handler instanceof HandlerMethod)) {
			return Boolean.TRUE;
		}

		HandlerMethod hm = (HandlerMethod) handler;
		ApiIdempotent anno = hm.getMethod().getAnnotation(ApiIdempotent.class);

		if (Objects.nonNull(anno)) {
			check(request);
		}

		return Boolean.TRUE;
	}

	/**
	 * 幂等性校验，检验通过放行，失败则抛异常
	 * @param request
	 */
	private void check(HttpServletRequest request) {
		tokenService.checkToken(request);
	}
}
