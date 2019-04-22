package com.ypb.concurrent.stage2.chapter4;

import lombok.Getter;

public abstract class ObservableRunnable implements Runnable {

	private final LifeCycleListener listener;

	public ObservableRunnable(final LifeCycleListener listener) {
		this.listener = listener;
	}

	protected void notifyChange(final RunnableEvent event) {
		listener.onEvent(event);
	}

	public enum RunnableStatus {
		RUNING, ERROR, DONE
	}

	public static class RunnableEvent {

		@Getter
		private final RunnableStatus status;
		@Getter
		private final Thread thread;
		@Getter
		private final Throwable cause;

		public RunnableEvent(RunnableStatus status, Thread thread, Throwable cause) {
			this.status = status;
			this.thread = thread;
			this.cause = cause;
		}
	}
}
