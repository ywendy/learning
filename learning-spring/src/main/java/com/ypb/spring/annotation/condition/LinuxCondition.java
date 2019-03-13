package com.ypb.spring.annotation.condition;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class LinuxCondition implements Condition {

	/**
	 * @param context 判断条件能使用的上下文对象
	 * @param metadata 注释信息
	 * @return
	 */
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

		// 能获取到ioc使用的beanFactory
		BeanFactory beanFactory = context.getBeanFactory();

		// 能获到到类加载器
		ClassLoader classLoader = context.getClassLoader();

		// 获取当前环境信息
		Environment environment = context.getEnvironment();

		// 获取bean定义的注册类
		BeanDefinitionRegistry registry = context.getRegistry();

		String osName = context.getEnvironment().getProperty("os.name");
		if (osName.toLowerCase().contains("linux".toLowerCase())) {
			return true;
		}
		return false;
	}
}
