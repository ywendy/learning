package com.ypb.idempotent.annoation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: ApiIdempotent
 * @Description: 定义需要保证接口幂等性的controller的方法上使用此注解
 * @author yangpengbing
 * @date 2019-06-12-14:33
 * @version V1.0.0
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiIdempotent {

}
