package com.ypb.spring.annotation.condition;

import com.ypb.spring.annotation.bean.RainBow;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
/**
 * @ClassName: CustomImportBeanDefinitionRegistrar
 * @Description: 手动将bean定义注册到ioc容器中
 * @author yangpengbing
 * @date 2019/3/18-10:53
 * @version V1.0.0
 *
 */
public class CustomImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

	/**
	 * @param importingClassMetadata 当前类的注解信息
	 * @param registry BeanDefinitionRegistry注册类，把所有需要添加到容器中的bean，调用BeanDefinitionRegistry.registerBeanDefinition手动注册
	 */
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		boolean isContainsRed = registry.containsBeanDefinition("com.ypb.spring.annotation.bean.Red");
		boolean isContainsBlue = registry.containsBeanDefinition("com.ypb.spring.annotation.bean.Blue");

		if (isContainsRed && isContainsBlue) {
			// 指定Bean定义信息。
			String beanName = "rainbow";
			BeanDefinition definition = new RootBeanDefinition(RainBow.class);

			registry.registerBeanDefinition(beanName, definition);
		}
	}
}
