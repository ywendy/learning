package com.ypb.spring.annotation.config;

import com.ypb.spring.annotation.bean.Car;
import com.ypb.spring.annotation.bean.CustomBeanPostProcessor;
import java.io.FileFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.core.type.filter.TypeFilter;

/**
 * @ClassName: MainConfigOfLifeCycle
 * @Description: Spring bean的生命周期
 * @author yangpengbing
 * @date 2019/3/18-15:09
 * @version V1.0.0
 *
 */
@Configuration
@ComponentScan(value = "com.ypb.spring.annotation.bean", useDefaultFilters = false, includeFilters = {
		@Filter(type = FilterType.ASSIGNABLE_TYPE, classes = CustomBeanPostProcessor.class)
})
public class MainConfigOfLifeCycle {

	@Bean(initMethod = "init", destroyMethod = "destroy")
//	@Scope("prototype")
	@Lazy
	public Car car() {
		return new Car();
	}
}

/**************************** 学习笔记(2019年3月18日) ******************************/
// bean的生命周期： bean的创建-->初始化-->销毁的过程
// 单实例bean是由Spring的容器管理。 我们可以自定义初始化和销毁的方法，容器在bean进行到当前生命周期的时候来调用我们自定义的的初始化和销毁的方法
// 单实例bean, 默认情况是容器启动的时候，创建对象
// 多实例bean，在每次获取对象的时候创建对象。

// 初始化
//  调用初始化的代码在org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean()中
//  BeanPostProcessor.postProcessBeforeInitialization, 对象创建完成，并赋值好，调用初始化方法之前调用此方法
//  调用对象的初始化方法
//  BeanPostProcessor.postProcessAfterInitialization。 初始化完成后，调用此方法

// 销毁
//  单实例的bean，容器关闭的时候，
//  多实例的bean，容器不会管理这个bean，不会调用销毁方法。

// 遍历容器中的所有的BeanPostProcessor：挨个执行postProcessBeforeInitialization, 一旦返回null。跳出for循环，不会执行后面的BeanPostProcessor.postProcessBeforeInitialization

// BeanPostProcessor原理
	// 给bean的属性赋值
//  populateBean(beanName, mbd, instanceWrapper);
//  initializeBean(){
		// 执行初始化方法之前，执行
//		applyBeanPostProcessorBeforeInitialization(wrappedBean, beanName);
		// 执行自定义初始化方法
//		invokeInitMethods(beanName, wrappedBean, mbd);
//		applyBeanPostProcessorAfterInitialization(wrappedBean, beanName);
//  }

// 1. 指定初始化和销毁方法， 通过@Bean指定initMethod和destroyMethod
// 2. 通过让Bean实现InitializingBean(定义初始化逻辑)和DisposableBean(定义销毁逻辑)
// 3. 可以使用JSR250： @PostConstructor：在bean创建完成并属性赋值完成，来执行初始化方法， @PreDestroy： 在容器销毁Bean之前通知我们进行清理工作。
// 4. BeanPostProcessor[interface]: bean的后置处理器
//	  在bean初始化前后进行一些处理工作：
//	    postProcessorBeforeInitialization： 在初始化之前工作
//      postProcessorAfterInitialization： 在初始化之后工作

// Spring底层对BeanPostProcessor的使用。
//	    bean赋值，注入其他组件，@Autowired，生命周期注解给你，@Async. ***BeanPostProcessor

