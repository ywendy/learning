package com.ypb.token.annoation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: NoRepeatSubmit
 * @Description: 定义不能重复提交的注解
 * @author yangpengbing
 * @date 2019-06-12-10:43
 * @version V1.0.0
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NoRepeatSubmit {

	/**
	 * 设置请求锁定的时间，单位秒
	 * @return
	 */
	int lockTime() default 10;
}
