package com.ypb.concurrent.stage3.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @ClassName: BlockingQueueTest
 * @Description: 阻塞队列测试(http://blog.csdn.net/defonds/article/details/44021605)
 * @author 杨鹏兵
 * @date 2017年6月30日-下午5:42:14
 * @version V1.0.0
 *
 */
public class BlockingQueueTest {
	public static void main(String[] args) throws Exception {
		int capacity = 1024; //队列容量
		boolean fair = false; //是否是公平锁
		BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(capacity, fair);
		
		Producer producer = Producer.of(queue);
		Customer customer = Customer.of(queue);
		
		new Thread(producer).start();
		new Thread(customer).start();
		
		Thread.sleep(4000);
	}
}

/**
 * @ClassName: Producer
 * @Description: 生产线程
 * @author 杨鹏兵
 * @date 2017年6月30日-下午5:51:10
 * @version V1.0.0
 * @param <T>
 * @param <T>
 *
 */
@Data@Builder@Accessors(chain = true)@AllArgsConstructor@RequiredArgsConstructor(staticName = "of")
class Producer implements Runnable{
	@NonNull
	@SuppressWarnings("rawtypes")
	private BlockingQueue queue;
	private int times = 3;

	@Override
	@SuppressWarnings("unchecked")
	public void run() {
		try {
			// 不能使用循环
			queue.put(1);
			Thread.sleep(1000);
			queue.put(2);
			Thread.sleep(1000);
			queue.put(3);
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

/**
 * @ClassName: Producer
 * @Description: 消费线程
 * @author 杨鹏兵
 * @date 2017年6月30日-下午5:51:10
 * @version V1.0.0
 * @param <T>
 * @param <T>
 *
 */
@Data@Builder@Accessors(chain = true)@RequiredArgsConstructor(staticName = "of")
class Customer implements Runnable{
	@NonNull
	@SuppressWarnings("rawtypes")
	private BlockingQueue queue;

	@Override
	public void run() {
		try {
			// 不能使用循环
			System.out.println(queue.take());
			System.out.println(queue.take());
			System.out.println(queue.take());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
