package com.ypb.designpattern.proxy.jdk;

import com.ypb.designpattern.proxy.jdk.demo.BeautifulGirl;
import com.ypb.designpattern.proxy.jdk.demo.IPerson;
import java.lang.reflect.Proxy;

public class FindLoveTest2 {

	public static void main(String[] args) {
		IPerson person = new BeautifulGirl();

		Class<? extends IPerson> clazz = person.getClass();
		IPerson proxy = (IPerson) Proxy
				.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), new DynamicProxy(person));

		System.out.println(proxy.getClass());
		proxy.findLove();
	}
}
