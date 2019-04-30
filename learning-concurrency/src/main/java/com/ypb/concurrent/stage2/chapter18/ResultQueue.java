package com.ypb.concurrent.stage2.chapter18;

import com.google.common.collect.Lists;
import java.util.LinkedList;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResultQueue {

	private static final int MAX_RESULT_SIZE = 100;
	private final LinkedList<Result> queues;

	public ResultQueue() {
		this.queues = Lists.newLinkedList();
	}

	public synchronized void put(Result result) {
		while (queues.size() > MAX_RESULT_SIZE) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				log.debug(e.getMessage(), e);
			}
		}

		queues.addLast(result);
		this.notifyAll();
	}

	public synchronized Result take() {
		while (queues.isEmpty()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				log.debug(e.getMessage(), e);
			}
		}

		Result result = queues.removeFirst();
		this.notifyAll();

		return result;
	}
}
