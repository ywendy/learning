package com.ypb.interview.blockcode;

public class HelloB extends HelloA {

	static {
		String msg = "HelloB类的静态代码块";
		System.out.println(msg);
	}

	{
		String msg = "HelloB类的构造代码块";
		System.out.println(msg);
	}

	public HelloB() {
		String msg = "HelloB类的无参构造方法";
		System.out.println(msg);
	}

	@Override

	public void hello() {
		{
			String msg = "HelloB类的普通代码块";
			System.out.println(msg);
		}
	}
}
