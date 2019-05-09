package com.ypb.designpattern.strategy.service;

import com.google.common.collect.Maps;
import com.ypb.designpattern.strategy.HandlerType;
import com.ypb.designpattern.strategy.enums.OrderTypeEnum;
import com.ypb.designpattern.strategy.utils.ClassScanner;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HandlerProcessor implements BeanFactoryPostProcessor {

	private static final String HANDLER_PACKAGE = "com.ypb.designpattern.strategy.handler";

	/**
	 * 扫描@handlerType注解，初始化HandlerContext，将其注册到Spring容器中
	 * @param beanFactory
	 * @throws BeansException
	 */
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		Map<OrderTypeEnum, Class> handlerMap = Maps.newHashMapWithExpectedSize(OrderTypeEnum.values().length);
		ClassScanner.scan(HANDLER_PACKAGE, HandlerType.class).forEach(clazz -> {
			OrderTypeEnum value = clazz.getAnnotation(HandlerType.class).value();

			handlerMap.put(value, clazz);
		});

		HandlerContext context = new HandlerContext(handlerMap);
		beanFactory.registerSingleton(HandlerContext.class.getName(), context);
	}
}
