package com.ypb.designpattern.strategy.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public final class SpringContextHolder implements ApplicationContextAware, DisposableBean {

	private static ApplicationContext applicationContext = null;

	/**
	 * @Title: initSysComponent
	 * @Description: 初始化系统组件
	 * @author 杨鹏兵
	 * @date 2016年9月8日-下午5:04:34
	 *
	 */
	private static void initSysComponent(){
		log.info("init applicationContext");
		try {
			getBean(SpringContextAware.class).initialize();;
		} catch (Exception e) {
			log.info("No spring components to init...");
		}
	}

	/**
	 * @Title: destorySysComponent
	 * @Description: 销毁系统组件
	 * @author 杨鹏兵
	 * @date 2016年9月8日-下午5:17:30
	 *
	 */
	private static void destorySysComponent(){
		try {
			getBean(SpringContextAware.class).destroy();
		} catch (Exception e) {
			log.info("No spring components to destroy...");
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		log.debug("注入ApplicationContext到SpringContextHolder:" + applicationContext);

		if (SpringContextHolder.applicationContext != null) {
			log.debug("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:" + SpringContextHolder.applicationContext);
		}

		// NICOBAR
		SpringContextHolder.applicationContext = applicationContext;
		// 初始化系统组件
		initSysComponent();
	}

	@Override
	public void destroy() {
		destorySysComponent();
		SpringContextHolder.clear();
	}

	/**
	 * @Title: clear
	 * @Description: 清除SpringContextHolder中的ApplicationContext为Null.
	 * @author 杨鹏兵
	 * @date 2016年9月8日-下午5:21:09
	 *
	 */
	public static void clear() {
		log.debug("清除SpringContextHolder中的ApplicationContext:" + applicationContext);
		applicationContext = null;
	}

	/**
	 * @Title: getBean
	 * @Description: 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 * @author 杨鹏兵
	 * @param <T>
	 * @date 2016年9月8日-下午5:10:20
	 *
	 */
	public static <T> T getBean(Class<T> requestType) {
		assertContextInjected();
		return applicationContext.getBean(requestType);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		assertContextInjected();
		return (T) applicationContext.getBean(name);
	}

	/**
	 * @Title: getApplicationContext
	 * @Description: 取得存储在静态变量中的ApplicationContext.
	 * @author 杨鹏兵
	 * @date 2016年9月8日-下午5:23:31
	 * @return
	 *
	 */
	public static ApplicationContext getApplicationContext() {
		assertContextInjected();
		return applicationContext;
	}

	/**
	 * @Title: assertContextInjected
	 * @Description: 验证applicaitonContext是否为空
	 * @author 杨鹏兵
	 * @date 2016年9月8日-下午5:12:24
	 *
	 */
	private static void assertContextInjected() {
		org.springframework.util.Assert.notNull(applicationContext, "applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
	}
}
