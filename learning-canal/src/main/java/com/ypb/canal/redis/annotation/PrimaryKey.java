package com.ypb.canal.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: PrimaryKey
 * @Description: 主键注解
 * @author yangpengbing
 * @date 2019-06-04-10:53
 * @version V1.0.0
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PrimaryKey {

}
