package com.ypb.spring.annotation;

import com.ypb.spring.annotation.bean.Persion;
import com.ypb.spring.annotation.config.MainConfig;
import com.ypb.spring.annotation.config.MainConfig2;
import java.util.stream.Stream;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @className IOCTest
 * @description IOC容器测试
 * @author yangpengbing
 * @date 23:55 2019/3/9
 * @version 1.0.0
 */
public class IOCTest {

	@Test
	public void testImport() {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfig2.class);

		System.out.println("IOC容器创建完成...");

		Object bean1 = ctx.getBean("colorFactoryBean");
		Object bean2 = ctx.getBean("colorFactoryBean");

		System.out.println("bean.getClass() = " + bean2.getClass());

		System.out.println("bean1 == bean2 = " + (bean1 == bean2));
	}

	@Test
	public void test02() {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfig2.class);

		System.out.println("IOC容器创建完成...");

		printAllBeanNames(ctx);
	}

	@Test
	public void test01() {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfig2.class);

		System.out.println("IOC容器创建完成...");

		if (ctx.containsBean("persion02")) {
			Persion persion = (Persion) ctx.getBean("persion02");
		}

		printAllBeanNames(ctx);
	}

	/**
	 * 测试
	 */
	@Test
	public void test() {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfig2.class);

		System.out.println("IOC容器创建完成...");
		Persion persion1 = (Persion) ctx.getBean("persion");
		Persion persion2 = (Persion) ctx.getBean("persion");

		System.out.println("persion1 == persion2 = " + (persion1 == persion2));
	}

	/**
     * 测试Configuration
     */
    @Test
    public void testConfiguration() {
	    ApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfig.class);

	    printAllBeanNames(ctx);
    }

	private void printAllBeanNames(ApplicationContext ctx) {
		Stream.of(ctx.getBeanDefinitionNames()).forEach(System.out::println);
	}
}
