package com.ypb.idempotent.annoation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: AccessLimit
 * @Description: 接口防刷注解
 * @author yangpengbing
 * @date 2019-06-12-16:30
 * @version V1.0.0
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {

	/**
	 * 最大访问次数
	 * @return
	 */
	int maxCount() default 10;

	/**
	 * 固定时间，单位秒
	 * @return
	 */
	long times() default 1L;

	/**
	 * 时间单位
	 * @return
	 */
	TimeUnit unit() default TimeUnit.SECONDS;
}
