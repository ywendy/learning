package com.ypb.spring.annotation;

import java.util.stream.Stream;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringAnnotationMain {

    public static void main(String[] args) {
        // 传统方式获取Spirng bean
        String configLocation = "applicationContext.xml";
        String beanName = "persion";

        ApplicationContext ctx = new ClassPathXmlApplicationContext(configLocation);

	    printAllBeans(ctx);
    }

	/**
	 * 输出IOC容器中的所有组件
	 * @param ctx
	 */
	private static void printAllBeans(ApplicationContext ctx) {
		String[] names = ctx.getBeanDefinitionNames();
		Stream.of(names).forEach(System.out::println);
	}
}
