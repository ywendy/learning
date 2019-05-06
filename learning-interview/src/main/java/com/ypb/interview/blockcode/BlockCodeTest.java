package com.ypb.interview.blockcode;

/**
 * @author yangpengbing
 * @version V1.0.0
 * @ClassName: BlockCodeTest
 * @Description: 代码块的测试类
 * @date 2019-04-26-17:31
 */
public class BlockCodeTest {

	public static int x = 10;

	static {
		printConsole("main方法中的静态代码块");

		System.out.println("x = " + x);
		x = 10 + 1;

		// 可以对静态代码块之后的变量赋值，但是不能访问
		y = 20 + 1;

		// Illegal forward reference
//		System.out.println("y = " + y);
	}

	private static int y = 20;

	public static void main(String[] args) {
		{
			printConsole("main方法中的普通代码块1");
		}

		new HelloB();

		{
			printConsole("main方法中的普通代码块2");
		}

	}

	private static void printConsole(String msg) {
		System.out.println(msg);
	}

	/**************************** 学习笔记(2019年4月26日) ******************************/
//  java中的静态代码块，构造方法代码块，普通代码块和同步代码块
//	加了static的是静态代码块，在类中写了一对{}是构造代码块，在方法中写了一对{}是普通代码块，
//	java中还存在一种代码块是同步代码块，常用在多线程中，synchronized关键字
//	同步代码块格式是synchronized(同步对象){}
//  静态代码块 先于 构造代码块 先于 构造方法执行
//	静态代码块 先于 普通代码块 先于 构造方法执行
//	构造代码块和普通代码块按照程序逻辑顺序执行

//	总结： 父类的静态代码块> 子类的静态代码块 > 父类的构造代码块 > 父类的构造方法 > 子类的构造代码块 > 子类的构造方法

//	原因分析：这其实主要是考虑类加载的问题

//	类初始化阶段主要是执行类构造器<clinit>方法，<clinit>方法主要是有类变量赋值和静态代码块中语句合并生成的
//	虚拟机会保证子类的<clinit>方法执行之前，父类的<clinit>方法已经执行完毕

//	一个类实例化的前提肯定是类已经正确被加载过的，所以<clinit>方法优先于<init>的方法

//	接口不能编写静态代码块
//	接口类的<clinit>方法，是不先调用父类的<clinit>方法的。 接口的实现类也一样不会先执行父类(接口)的<clinit>方法。只有到静态变量被使用的时候才会被初始化

//	jvm会保证一个类被加载一次，多个线程加载同一个类，锁，其他的等待

//	静态代码块中只能访问定义在静态代码块之前的变量，定义在它之后的变量只能赋值，不能访问
}

