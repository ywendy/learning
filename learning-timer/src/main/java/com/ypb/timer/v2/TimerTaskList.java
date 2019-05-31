package com.ypb.timer.v2;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
/**
 * @ClassName: TimerTaskList
 * @Description: 双向链表
 * @author yangpengbing
 * @date 2019-05-31-10:13
 * @version V1.0.0
 *
 */
public class TimerTaskList implements Delayed {

	private AtomicInteger taskCounter;
	private TimerTaskEntity root;
	private AtomicLong expiration;

	/**
	 * TimerTaskList forms a doubly linked cyclic list using a dummy root entity
	 * root.next points to the head
	 * root.prev points to the tail
	 * @param taskCounter
	 */
	public TimerTaskList(AtomicInteger taskCounter) {
		this.taskCounter = taskCounter;
		this.root = new TimerTaskEntity(null, -1L);
		this.root.next = root;
		this.root.prev = root;
		this.expiration = new AtomicLong(-1L);
	}

	/**
	 * set bucket expiration time
	 * @param expirationMs
	 * @return true expiration time changed
	 */
	public boolean setExpiration(Long expirationMs) {
		return expiration.getAndSet(expirationMs) != expirationMs;
	}

	public Long getExpiration() {
		return expiration.get();
	}

	public synchronized void flush(Function<TimerTaskEntity, Void> f) {
		TimerTaskEntity head = root.next;
		while (head != root) {
			remove(head);
			f.apply(head);
			head = root.next;
		}

		expiration.set(-1L);
	}

	/**
	 * remove the specified timer task entry from this list
	 * @param entity
	 */
	public synchronized void remove(TimerTaskEntity entity) {
		synchronized (entity) {
			if (entity.getList() == this) {
				entity.next.prev = entity.prev;
				entity.prev.next = entity.next;

				entity.next = null;
				entity.prev = null;

				entity.setList(null);

				taskCounter.decrementAndGet();
			}
		}
	}

	/**
	 * add a timer task entity to this list
	 * @param entity
	 */
	public void add(TimerTaskEntity entity) {
		boolean done = Boolean.FALSE;
		while (!done) {
			// remove the timer task entity if it is already in any other list
			// we do this outside of the sync block below to avoid deadlocking.
			// we may retry until timerTaskEntity.list becomes null.
			entity.remove();

			synchronized (this) {
				synchronized (entity) {
					if (Objects.isNull(entity.getList())) {
						// put the timer task entity to the end of the list.(root.prev points to the tail entity)
						TimerTaskEntity tail = root.prev;
						entity.next = root;
						entity.prev = tail;
						entity.setList(this);

						tail.next = entity;
						root.prev = entity;
						taskCounter.incrementAndGet();
						done = Boolean.TRUE;
					}
				}
			}
		}
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(Long.max(getExpiration() - Time.getHiresClockMs(), BigDecimal.ZERO.longValue()),
				TimeUnit.MILLISECONDS);
	}

	@Override
	public int compareTo(Delayed d) {
		TimerTaskList other;
		if (d instanceof TimerTaskList) {
			other = (TimerTaskList) d;
		} else {
			throw new ClassCastException("can not cast to TimerTaskList.");
		}

		if (getExpiration() < other.getExpiration()) {
			return -1;
		} else if (getExpiration() > other.getExpiration()) {
			return 1;
		}

		return 0;
	}
}
