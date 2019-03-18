package com.ypb.spring.annotation.config;

import com.ypb.spring.annotation.bean.Car;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName: MainConfigOfLifeCycleTest
 * @Description: 测试bean的生命周期
 * @author yangpengbing
 * @date 2019/3/18-15:19
 * @version V1.0.0
 *
 */
public class MainConfigOfLifeCycleTest {

	@Test
	public void test1() {
		// 创建IOC容器
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfigOfLifeCycle.class);

		Car car = (Car) ctx.getBean("car");

//		((AnnotationConfigApplicationContext) ctx).close();
	}

}