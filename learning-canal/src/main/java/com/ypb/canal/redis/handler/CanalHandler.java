package com.ypb.canal.redis.handler;

import com.alibaba.google.common.collect.Queues;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.ypb.canal.redis.context.HandlerContext;
import com.ypb.canal.redis.utils.SpringContextHolder;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CanalHandler {

	private static final Integer MAX_QUEUE_SIZE = 1024;

	private BlockingQueue<Entry> queue;
	private int pollingDelayMillis = 500;
	private PollingThread pollingThread;
	private HandlerContext handlerContext;

	private CanalHandler() {
		queue = Queues.newLinkedBlockingDeque(MAX_QUEUE_SIZE);
		handlerContext = SpringContextHolder.getBean(HandlerContext.class);

		String schedulerName = "canal-polling";
		pollingThread = new PollingThread();
		pollingThread.setName(schedulerName);
		pollingThread.start();
	}

	private static class CanalHolder {
		private static final CanalHandler handler = new CanalHandler();
	}

	public static CanalHandler getInstance() {
		return CanalHolder.handler;
	}

	public void addEntry(Entry entry) {
		try {
			queue.put(entry);
		} catch (InterruptedException e) {
			log.debug(e.getMessage(), e);
		}
	}

	private void handlerMessage(Entry entry) {
		CompletableFuture.runAsync((() -> {
			AbstractHandler handler = handlerContext.getInstance(entry.getHeader().getEventType());
			handler.handlerMessage(entry);
		}));
	}

	private class PollingThread extends Thread {

		@Override
		public void run() {
			try {
				while (true) {
					Entry entry = queue.take();
					handlerMessage(entry);
				}
			} catch (Exception e) {
				log.debug(e.getMessage(), e);
			}
		}
	}
}
