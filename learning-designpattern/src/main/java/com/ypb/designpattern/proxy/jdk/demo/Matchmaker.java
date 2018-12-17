package com.ypb.designpattern.proxy.jdk.demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.MessageFormat;

/**
 * @author yangpengbing
 * @version V1.0.0
 * @ClassName: Matchmaker
 * @Description: 定义媒婆对象实现InvocationHandler接口
 * @date 2018/12/14-15:20
 */
public class Matchmaker implements InvocationHandler {

	private IPerson target;

	public Object getInstance(IPerson target) {
		this.target = target;
		Class<? extends IPerson> clazz = target.getClass();
		System.out.println(MessageFormat.format("被代理对象的class是{0}", clazz));

		return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		String msg = "我是媒婆，得给你找个异性才行";
		System.out.println(msg);

		msg = "开始进行海选...";
		System.out.println(msg);

		msg = "-----------------";
		System.out.println(msg);

		method.invoke(this.target, args);
		System.out.println(msg);

		msg = "如果合适的话，就准备办事";
		System.out.println(msg);

		return proxy;
	}
}
