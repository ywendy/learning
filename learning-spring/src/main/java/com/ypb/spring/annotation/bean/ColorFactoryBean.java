package com.ypb.spring.annotation.bean;

import org.springframework.beans.factory.FactoryBean;
/**
 * @ClassName: ColorFactoryBean
 * @Description: 创建一个Spring定义的FactoryBean
 * @author yangpengbing
 * @date 2019/3/12-18:38
 * @version V1.0.0
 *
 */
public class ColorFactoryBean implements FactoryBean {

	/**
	 * @return 返回一个Color对象，这个对象会添加到IOC容器中
	 * @throws Exception
	 */
	@Override
	public Color getObject() throws Exception {
		System.out.println("给容器中添加Color...");

		return new Color();
	}

	@Override
	public Class<?> getObjectType() {
		return Color.class;
	}

	/**
	 * 是否是单例
	 * true：是单例，这个bean是单例的，在容器中保存一份
	 * false：多实例，每次获取都会创建一个新的Bean
	 * @return
	 */
	@Override
	public boolean isSingleton() {
		return false;
	}
}
