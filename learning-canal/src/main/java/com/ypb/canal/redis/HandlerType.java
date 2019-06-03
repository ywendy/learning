package com.ypb.canal.redis;

import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: HandlerType
 * @Description: 定义注解
 * @author yangpengbing
 * @date 2019-06-03-16:29
 * @version V1.0.0
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface HandlerType {

	EventType value() default EventType.INSERT;
}
