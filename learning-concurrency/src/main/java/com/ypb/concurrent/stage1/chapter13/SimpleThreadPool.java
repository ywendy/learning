package com.ypb.concurrent.stage1.chapter13;

import com.google.common.collect.Lists;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yangpengbing
 * @version V1.0.0
 * @ClassName: SimpleThreadPool
 * @Description: 实现简单的Thread线程池
 * @date 2019-05-06-14:28
 */
@Slf4j
public class SimpleThreadPool extends Thread {

	private int size;
	private final int queueSize;
	private final DiscardPolicy policy;
	private static volatile int seq = 0;
	private volatile boolean destroy = false;

	private int min;
	private int active;
	private int max;

	private final LinkedList<Runnable> taskQueue = Lists.newLinkedList();
	private final List<WorkerTask> workerTasks = Lists.newLinkedList();

	private static final int DEFAULT_TASK_QUEUE_SIZE = 2000;
	private static final DiscardPolicy DEFAULT_DISCARDPOLICY = () -> {
		throw new DiscardException("discard this task.");
	};
	private static final ThreadGroup GROUP = new ThreadGroup("pool-group");
	private static final String THREAD_PREFIX = "simple-thread-pool-";

	public SimpleThreadPool() {
		this(4, 8, 12, DEFAULT_TASK_QUEUE_SIZE, DEFAULT_DISCARDPOLICY);
	}

	public SimpleThreadPool(int min, int active, int max, int queueSize, DiscardPolicy policy) {
		this.min = min;
		this.active = active;
		this.max = max;
		this.queueSize = queueSize;
		this.policy = policy;

		init();
	}

	private void init() {
		for (int i = 0; i < min; i++) {
			createWorkerTask();
		}

		this.size = min;
		this.start();
	}

	private void createWorkerTask() {
		String name = THREAD_PREFIX + (++seq);
		WorkerTask task = new WorkerTask(GROUP, name);
		task.start();

		workerTasks.add(task);
	}

	public void submit(Runnable runnable) {
		if (destroy) {
			throw new IllegalStateException("The thread poll already destroy and not allow submit task.");
		}

		synchronized (taskQueue) {
			if (taskQueue.size() > queueSize) {
				policy.discard();
			}

			taskQueue.addLast(runnable);
			taskQueue.notifyAll();
		}
	}

	public void shutdown() throws InterruptedException {
		while (!taskQueue.isEmpty()) {
			Thread.sleep(50);
		}

		synchronized (workerTasks) {
			int initVal = workerTasks.size();
			while (initVal > 0) {
				for (WorkerTask task : workerTasks) {
					if (task.getTaskState() == TaskState.BLOCKED) {
						task.interrupt();
						task.close();

						initVal--;
					} else {
						Thread.sleep(10);
					}
				}
			}
		}

		destroy = true;
	}

	@Override
	public void run() {
		while (!destroy) {
			printLog();

			try {
				Thread.sleep(100);

				if (taskQueue.size() > active && size < active) {
					for (int i = size; i < active; i++) {
						createWorkerTask();
					}

					printLog("the pool incremented to active.");
					size = active;
				} else if (taskQueue.size() > max && size < max) {
					for (int i = size; i < max; i++) {
						createWorkerTask();
					}

					printLog("the pool incremented to max.");
					size = max;
				}

				synchronized (workerTasks) {
					if (taskQueue.isEmpty() && size > active) {
						printLog("=======reduce=============");

						int releaseSize = active - size;
						for (Iterator<WorkerTask> wts = workerTasks.iterator(); wts.hasNext(); ) {
							if (releaseSize <= BigDecimal.ZERO.intValue()) {
								break;
							}

							WorkerTask task = wts.next();
							task.close();
							task.interrupt();
							wts.remove();
							releaseSize--;
						}

						size = active;
					}
				}
			} catch (InterruptedException e) {
				log.debug(e.getMessage(), e);
			}
		}
	}

	private void printLog() {
		log.info("pool min {}, active {}, max {}, current {}, queueSize {}", min, active, max, size, taskQueue.size());
	}

	private void printLog(String msg) {
		log.info(msg);
	}

	private static class DiscardException extends RuntimeException {

		public DiscardException(String message) {
			super(message);
		}
	}

	/**
	 * 拒绝策略
	 */
	@FunctionalInterface
	private interface DiscardPolicy {

		void discard();
	}

	private class WorkerTask extends Thread {

		@Getter
		private volatile TaskState taskState = TaskState.FREE;

		public WorkerTask(ThreadGroup group, String name) {
			super(group, name);
		}

		@Override
		public void run() {
			Runnable runnable = null;
			try {
				while (this.taskState != TaskState.DEAD) {
					synchronized (taskQueue) {
						while (taskQueue.isEmpty()) {
							taskState = TaskState.BLOCKED;

							taskQueue.wait();
						}

						runnable = taskQueue.removeFirst();
					}
				}
			} catch (InterruptedException e) {
				printLog("Closed.");
			}

			if (Objects.isNull(runnable)) {
				return;
			}

			taskState = TaskState.RUNNING;
			runnable.run();
			taskState = TaskState.FREE;
		}

		void close() {
			taskState = TaskState.DEAD;
		}
	}

	private enum TaskState {
		FREE, RUNNING, BLOCKED, DEAD;
	}


	public static void main(String[] args) throws InterruptedException {
		SimpleThreadPool pool = new SimpleThreadPool();
		for (int i = 0; i < 40; i++) {
			pool.submit(()->{
				System.out.println("The runnable be serviced by " + Thread.currentThread() + " start.");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					log.debug(e.getMessage(), e);
				}

				System.out.println("The runnable be serviced by " + Thread.currentThread() + " finished.");
			});
		}

		Thread.sleep(10000);
		pool.shutdown();
	}

	/**************************** 学习笔记(2019年5月6日) ******************************/
//	线程池的要求：
//	1. core核心线程数， 队列
}

