package com.ypb.concurrent.chapter7;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: SynchronizedStatic
 * @Description: 测试synchronized同步锁
 * @author yangpengbing
 * @date 2019-04-10-17:11
 * @version V1.0.0
 *
 */
@Slf4j
public class SynchronizedStaticTest {

	public static void main(String[] args) {
		new Thread(() -> SynchronizedStatic.m1(), "T1").start();
		new Thread(() -> SynchronizedStatic.m2(), "T2").start();
	}

	/**************************** 学习笔记(2019年4月10日) ******************************/
//	https://www.jianshu.com/p/d53bf830fa09
//	https://baijiahao.baidu.com/s?id=1612142459503895416&wfr=spider&for=pc

//	Synchronized实现原理
//	    在java代码中使用synchronized可是使用在代码和方法中，根据Synchronized用的位置可以有如下场景：
//		1. synchronized作用在实例方法上， 被锁定的对象是类的实例对象，
//		2. synchronized作用在静态方法上，被锁定的是类对象。也就是类的字节码
//		3. synchronized作用在同步代码块上，被锁定的对象可以是任意对象。
//		4. synchronized作用在静态代码块上，被锁定的是类对象。

//	如果对象监视器(锁)的是类对象，无论new多少实例对象，但他们都属于同一类依然被锁住，即线程之间保证同步关系。

//	使用javap -c (c是对代码进行反汇编) javap -c SynchronizedDemo

//	执行同步代码块后首先要先执行monitorenter指令，退出的时候monitorexit指令。通过分析可以得知，使用synchronized进行同步之后，其关键就是必须要对对象监视器monitor进行获取
//	当前线程获取到monitor后才能继续往下执行，否则就只能等待，而这个获取的过程是互斥的，即同一时刻，只有一个线程能够获取到monitor，如果这个对象没有被锁定，或者当前对象已经拥有了这个对象
//	锁，那么就把锁的计数器加1，当然与之对应的执行monitorexit指令，锁的计数器减1

//	查看字节码可以看到，有两个monitorexit指令，这是为什么呢?
//	这是因为编译器需要确保调用过程中的每条monitorenter指令都要执行对应的monitorexit指令，为了保证在方法异常时，monitorenter和monitorexit指令也能正常配对执行，编译器会自动产生一个异常处理器，
//	它的目的就是用来执行异常的monitorexit指令，而字节码中多出的monitorexit指令，就是异常结束时，被执行用来是否monitorenter的。

//	javap -p -v SynchronizedDemo  修饰在方法上面的是ACC_SYNCHRONIZED标识

//	monitor是有ObjectMonitor实现(c++实现的)，对于我们来说，主要关注的是如下代码：
//	ObjectMonitor(){
//		省略部分变量
//		_count          = 0;
//		_owner          = null;
//		_WaitSet        = null;
//		_WaitSetLock    = 0;
//		_EntityList     = null;
//	}

//	_WaitSet队列用来保存每个等待锁的线程对象
//	_owner, 他指向持有ObjectMonitor对象的线程，当多个线程访问同一个代码是，会先存放到_EntityList集合中，接下来当线程获取到monitor时，就会吧_owner变量设置为当前线程，
//	同时_count变量+1，如果线程调用wait方法，就会释放当前持有的monitor，那么_owner变量就会被设置为null，同时_count-1，并且该线程进入_WaitSet集合中，等待下一次被唤醒。

}

@Slf4j
class SynchronizedStatic {

	// 同步代码块
	static {
		synchronized (SynchronizedStaticTest.class) {
			System.out.println("static " + Thread.currentThread().getName());
			sleep();
		}
	}

	private static void sleep() {
		try {
			Thread.sleep(10_000);
		} catch (InterruptedException e) {
			log.debug(e.getMessage(), e);
		}
	}

	public synchronized static void m1() {
		System.out.println("m1 " + Thread.currentThread().getName());
		sleep();
	}

	public static void m2(){
		System.out.println("m2 " + Thread.currentThread().getName());
		sleep();
	}
}
