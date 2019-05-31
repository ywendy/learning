package com.ypb.concurrent.stage3.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: DelayQueueTest
 * @Description: 延迟队列测试
 * @author 杨鹏兵
 * @date 2017年7月3日-上午11:00:11
 * @version V1.0.0
 *
 */
public class DelayQueueTest {

	public static void main(String[] args) throws Exception {
		BlockingQueue<Delayed> queue = new DelayQueue<>();
		queue.put(new DelayedElement()); // DelayedElement 是我所创建的一个 DelayedElement 接口的实现类，它不在 Java.util.concurrent 包里。你需要自行创建你自己的 Delayed 接口的实现以使用 DelayQueue 类

		System.out.println(queue.take());
	}
}

class DelayedElement implements Delayed{

	@Override
	public int compareTo(Delayed o) {
		return 0;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return 0;
	}
	
}

/******************************* 学习笔记 **************************************/
//	DelayQueue实现了BlockingQueue接口
//	DelayQueue对元素进行持有直到一个特定的延迟到期。注入其中的元素必须实现java.util.concurrent.Delayed接口，该接口定义：
//		public interface Delayed extends Comparable<Delayed>{
//			public long getDelay(TimeUnit timeUnit);
//		}

//	DelayQueue将会在每个元素的getDelay()方法返回的值时间段之后才释放掉该元素，如果返回值是0或者负值，延迟将被认为过期，该元素将会在DelayQueue的下一次take被调用的时候释放掉。
//	传递给getDelay方法的getDelay实例是一个枚举类型，它表明了将要延迟的时间段，TimeUnit枚举将会取以下值：
//		DAYS(天), HOURS(小时), MINUTES( 分钟), SECONDS(秒), MILLISECONDS(毫秒   千分之一秒), MICROSECONDS(微秒   一百万分之一秒（就是毫秒/1000）), NANOSECONDS(毫微秒  十亿分之一秒（就是微秒/1000）)
//	正如你所看到的，Delayed接口实现了java.lang.Comparable接口，这也就意味着Delayed对象之间可以进行比较，这个可能在对DelayQueue队列中的元素进行排序有用，因此他们可以根据过期时间进行有序释放

//	DelayQueuel类的主要作用：是一个无界的BlockingQueue，用于放置实现类Delayed接口的对象，其中的对象只能在其到期时才能够从队列中取走，这种队列是有序的，即对头元素的延迟时间最长，注意不能将null元素到这种队列中
//	Delayed，一种混合风格的接口，用来标记那些应该在给定延迟时间之后执行的对象，此接口实现必须定义一个compareTo方法，该方法提供与此接口的getDelay方法一致的排序