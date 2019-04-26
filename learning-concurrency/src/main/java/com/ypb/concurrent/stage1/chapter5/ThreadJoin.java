package com.ypb.concurrent.stage1.chapter5;

import java.util.Optional;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yangpengbing
 * @version V1.0.0
 * @ClassName: ThreadJoin
 * @Description: Thread的join方法
 * @date 2019-04-09-11:42
 */
@Slf4j
public class ThreadJoin {

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = createThread();
		Thread t2 = createThread();

		t1.start();
		t2.start();
		t1.join();
		t2.join();

		Optional.of("All of tasks finish done.").ifPresent(System.out::println);
		createTask();
	}

	private static Thread createThread() {
		return new Thread(ThreadJoin::createTask);
	}

	private static void createTask() {
		IntStream.range(1, 1000).forEach(
				i -> Optional.of(Thread.currentThread().getName() + "-->" + i).ifPresent(System.out::println));
	}

	/**************************** 学习笔记(2019年4月9日) ******************************/
//	java的join()方法
//	    thread.join()把指定的线程加入到当前线程，可以把两个交替执行的线程合并为顺序执行的线程。
//	    比如在线程B中调用了线程A的join方法，知道线程A执行完毕后，才能继续执行B线程

//	t.join(); 调用join方法，等待线程t执行完毕
//	t.join(1000); 等待t线程执行完毕，等待时间是1000毫秒

//	从join的源码中可以看出， 如果线程没有启动的话，调用join是没有作用的，将继续向下执行。
//	知道该对象唤醒main线程，比如退出后，这意味着main线程调用t.join时，必须能够拿到线程t的对象锁

//	join方法是对Object类中的wait方法封装的，wait操作，必须有synchronized与之对应
//	    join方法上添加了synchronized，锁的是this。这个this指的是t线程本身对象，也就是说，主线程持有了t这个对象的锁
//	    有wait方法必然有notify()，什么时候才有notify()呢，在JVM中 ensure_join方法中的 lock.notify_all(thread);
//  当t线程执行完毕的时候，JVM会自动唤醒被阻塞在t线程上的所有线程，t线程对象被notifyAll()了。

//	join方法实现是通过wait()。当main线程调用t.join时候，main线程会获得线程对象t的锁(wait意味着拿到该对象的锁)，调用该对象的wait(等待时间)

//	首先join是一个synchronized方法，里面调用了wait()，这个过程的目的是让持有这个同步锁的线程进入等待，那么谁持有了这个同步锁呢，答案是当前线程，
//	因为是当前线程调用了t.join()方法，相当于在t.join()代码中写入了一个同步代码块，谁去执行这段代码呢，是当前线程，所以主线程被wait()了，然后在t线程执行完毕后
//	JVM会调用lock.notify_all(thread); 唤醒持有t这个对象锁的所有线程，也就是当前线程，会继续执行。 这个说明在Thread的start()方法javadoc中有说明

}
