package com.ypb.concurrent.stage3.blockingqueue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: DelayQueueTestWithCache
 * @Description: 业务场景二：具有过期时间的缓存
	该场景来自于http://www.cnblogs.com/jobs/archive/2007/04/27/730255.html，向缓存添加内容时，给每一个key设定过期时间，系统自动将超过过期时间的key清除。
	这个场景中几个点需要注意：
    	当向缓存中添加key-value对时，如果这个key在缓存中存在并且还没有过期，需要用这个key对应的新过期时间
    	为了能够让DelayQueue将其已保存的key删除，需要重写实现Delayed接口添加到DelayQueue的DelayedItem的hashCode函数和equals函数
    	当缓存关闭，监控程序也应关闭，因而监控线程应当用守护线程
 * @author 杨鹏兵
 * @date 2017年7月3日-下午1:24:01
 * @version V1.0.0
 *
 */
public class DelayQueueTestWithCache<K, V> {

	private ConcurrentHashMap<K, V> map = new ConcurrentHashMap<>();
	private BlockingQueue<DelayedItem<K>> queue = new DelayQueue<>();
	
	public DelayQueueTestWithCache(){
		Thread t = new Thread(){

			@Override
			public void run() {
				dameonCheckOverdueKey();
			}
		};
		
		t.setDaemon(true);
		t.start();
	}
	
	/**
	 * @Title: dameonCheckOverdueKey
	 * @Description: TODO
	 * @author 杨鹏兵
	 * @date 2017年7月3日-下午1:52:29
	 * 
	 */
	protected void dameonCheckOverdueKey() {
		while(true){
			DelayedItem<K> dItem = queue.poll();
			if(null != dItem){
				map.remove(dItem.getT());
				System.out.println(System.nanoTime()+" remove "+dItem.getT() +" from cache");
			}
			
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void put(K k, V v, long liveTime) throws Exception{
		v = map.put(k, v);
		DelayedItem<K> dItem = new DelayedItem<>(k, liveTime);
		if(null != v){
			queue.remove(dItem);
		}
		queue.put(dItem);
	}
	
	public static void main(String[] args) throws Exception {
		Random r = new Random();
		int cacheNumber = 20;
		int liveTime = 0;
		DelayQueueTestWithCache<String, Integer> cache = new DelayQueueTestWithCache<>();
		for (int i = 0; i < cacheNumber; i++) {
			liveTime = r.nextInt(3000);
			System.out.println(i +"\t"+ liveTime);
			
			cache.put(i+"", i, liveTime);
		}
		
		Thread.sleep(30000);
	}
}

class DelayedItem<T> implements Delayed{

	@Getter@Setter
	private T t;
	private long liveTime;
	private long removeTime;
	
	public DelayedItem(T t, long liveTime){
		this.t = t;
		this.liveTime = liveTime;
		this.removeTime = TimeUnit.NANOSECONDS.convert(liveTime, TimeUnit.NANOSECONDS) + System.nanoTime();
	}
	
	@Override
	public int compareTo(Delayed o) {
		if(null == o) return 1;
		if(this == o) return 0;
		if(o instanceof DelayedItem){
			return (int) (this.liveTime - ((DelayedItem) o).liveTime);
		}
		return (int) (this.getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS));
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(removeTime - System.nanoTime(), TimeUnit.NANOSECONDS);
	}

	@Override
	public int hashCode() {
		return t.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof DelayedItem){
			return obj.hashCode() == this.hashCode() ? true : false;
		}
		return false;
	}
}