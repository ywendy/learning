package com.ypb.designpattern.proxy.jdk;

import com.ypb.designpattern.proxy.jdk.demo.IPerson;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @ClassName: DynamicProxy
 * @Description: 动态代理对象
 * @author yangpengbing
 * @date 2018/12/14-17:20
 * @version V1.0.0
 *
 */
public class DynamicProxy implements InvocationHandler {

	private final IPerson targert;

	public DynamicProxy(IPerson target) {
		this.targert = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		String msg = "before find love";
		System.out.println(msg);

		method.invoke(targert, args);

		msg = "after find love";
		System.out.println(msg);

		return targert;
	}
}
