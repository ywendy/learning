package com.ypb.token.aop;

import com.ypb.token.entry.ApiResult;
import com.ypb.token.utils.RedisLock;
import com.ypb.token.utils.RequestUtils;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @ClassName: RepeatSubmitAspect
 * @Description: 防重复提交的Aspect
 * @author yangpengbing
 * @date 2019-06-12-10:45
 * @version V1.0.0
 *
 */
@Slf4j
@Aspect
@Component
public class RepeatSubmitAspect {

	@Autowired
	private RedisLock redisLock;

	/**
	 * 定义切入点表达式
	 * @param noRepeatSubmit
	 */
	@Pointcut("@annotation(noRepeatSubmit)")
	public void pointCut(NoRepeatSubmit noRepeatSubmit) {}

	@Around("pointCut(noRepeatSubmit)")
	public Object around(ProceedingJoinPoint pjp, NoRepeatSubmit noRepeatSubmit) throws Throwable {
		int lockTime = noRepeatSubmit.lockTime();

		HttpServletRequest request = RequestUtils.getRequest();
		Assert.notNull(request, "request can not null.");

		String key  = getKey(request.getHeader("token"), request.getServletPath());
		String clientIp = getClientId();

		boolean success = redisLock.tryLock(key, clientIp, lockTime);
		log.info("tryLock key = [{}], clientIP = [{}], result = [{}]", key, clientIp, success);

		if (!success) {
			return new ApiResult<>(HttpStatus.OK.value(), "重复提交，请稍后在试!");
		}

		Object result;

		try {
			result = pjp.proceed();
		}finally {
			redisLock.releaseLock(key, clientIp);
		}

		return result;
	}

	private String getClientId() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 获取当前用户的 token（或者JSessionId）+ 当前请求地址，作为一个唯一 KEY
	 * @param token 用户登录的token
	 * @param path
	 * @return
	 */
	private String getKey(String token, String path) {
		return token + ":" + path;
	}
}
