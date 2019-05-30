package com.ypb.timer.v1;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Queues;
import java.util.concurrent.Delayed;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * @author yangpengbing
 * @version V1.0.0
 * @ClassName: Bucket
 * @Description: 槽
 * @date 2019/1/24-13:21
 */
@Slf4j
public class Bucket implements Delayed {

	private AtomicLong expiration = new AtomicLong(-1L);

	private LinkedBlockingQueue<TimedTask> tasks = Queues.newLinkedBlockingQueue();

	@Override
	public long getDelay(TimeUnit unit) {
		return Math.max(0, unit.convert(expiration.get() - System.currentTimeMillis(), TimeUnit.MILLISECONDS));
	}

	@Override
	public int compareTo(Delayed o) {
		if (o instanceof Bucket) {
			return Long.compare(expiration.get(), ((Bucket) o).expiration.get());
		}

		return 0;
	}

	/**
	 * 获取槽的过期时间
	 */
	public long getExpire() {
		return expiration.get();
	}

	/**
	 * 重新分配槽
	 */
	public synchronized void flush(Consumer<TimedTask> task) {
		for (TimedTask timedTask : tasks) {
			if (this.equals(timedTask.bucket)) {
				tasks.remove(timedTask);

				task.accept(timedTask);
			}
		}

		expiration.set(-1L);
	}

	/**
	 * 添加任务到槽
	 */
	public void addTask(TimedTask task) {
		tasks.offer(task);
	}

	/**
	 * 设置槽的过期时间
	 */
	public boolean setExpire(long expire) {
		return expiration.getAndSet(expire) != expire;
	}

	@Override
	public String toString() {
		String expireDate = new DateTime(expiration.get()).toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
		return MoreObjects.toStringHelper(this).add("过期时间", expireDate)
				.add("expiration", expiration.get())
				.addValue(String.format("延迟: {%s}s", getDelay(TimeUnit.SECONDS))).toString();
	}

}
