package com.ypb.spring.annotation.config;

import com.ypb.spring.annotation.bean.Persion;
import java.util.concurrent.CountDownLatch;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainConfigOfPropertyValuesTest {

	@Test
	public void persion() {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfigOfPropertyValues.class);
		Persion persion = (Persion) ctx.getBean("persion");

		System.out.println("persion = " + persion);
	}
}