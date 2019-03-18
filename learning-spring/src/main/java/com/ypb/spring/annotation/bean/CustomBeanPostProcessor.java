package com.ypb.spring.annotation.bean;

import java.text.MessageFormat;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @ClassName: CustomBeanPostProcessor
 * @Description: bean的后置处理器，初始化前后进行处理工作，将后置处理器加入的容器中
 * @author yangpengbing
 * @date 2019/3/18-16:46
 * @version V1.0.0
 *
 */
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		String msg = MessageFormat
				.format("CustomBeanPostProcessor...postProcessBeforeInitialization...beanName:{0}...bean:{1}", beanName,
						bean);

		System.out.println(msg);
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		String msg = MessageFormat
				.format("CustomBeanPostProcessor...postProcessAfterInitialization...beanName:{0}...bean:{1}", beanName,
						bean);
		System.out.println(msg);

		return bean;
	}
}
