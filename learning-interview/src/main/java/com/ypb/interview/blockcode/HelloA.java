package com.ypb.interview.blockcode;

public class HelloA {

	static {
		String msg = "HelloA类中的静态代码块";
		System.out.println(msg);
	}

	{
		String msg = "HelloA类中的构造代码块";
		System.out.println(msg);
	}

	public HelloA() {
		String msg = "HelloA类的无参数构造方法";
		System.out.println(msg);
	}

	public void hello(){
		{
			String msg = "HelloA类普通代码块";
			System.out.println(msg);
		}

		System.out.println("HelloA类的hello方法");
	}

}
