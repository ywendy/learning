package com.ypb.canal.redis.service;

import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.google.common.collect.Maps;
import com.ypb.canal.redis.HandlerType;
import com.ypb.canal.redis.utils.ClassScanner;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HandlerProcessor implements BeanFactoryPostProcessor {

	private static final String HANDLER_PACKAGE = "com.ypb.canal.redis.handler";

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

		Map<EventType, Class> handlerMap = Maps.newHashMapWithExpectedSize(EventType.values().length);
		ClassScanner.scan(HANDLER_PACKAGE, HandlerType.class).forEach(clazz -> {
			EventType type = clazz.getAnnotation(HandlerType.class).value();

			handlerMap.put(type, clazz);
		});

		HandlerContext context = new HandlerContext(handlerMap);
		beanFactory.registerSingleton(HandlerContext.class.getName(), context);
	}
}
