package com.ypb.concurrent.stage2.chapter4;

import com.ypb.concurrent.stage2.chapter4.ObservableRunnable.RunnableEvent;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

@Slf4j
public class ThreadLifeCycleObserver implements LifeCycleListener {

	private final Object LOCk = new Object();

	public void concurrentQuery(List<Integer> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return;
		}

		ids.stream().forEach(id -> new Thread(new ObservableRunnable(this) {

			@Override
			public void run() {
				Thread thread = Thread.currentThread();
				try {
					notifyChange(new RunnableEvent(RunnableStatus.RUNING, thread, null));

					businessCode(id);

					notifyChange(new RunnableEvent(RunnableStatus.DONE, thread, null));
				} catch (Exception e) {
					log.debug(e.getMessage(), e);
					notifyChange(new RunnableEvent(RunnableStatus.ERROR, thread, e));
				}
			}

			private void businessCode(Integer id) {
				System.out.println("query for the id " + id);
			}
		}, "" + id).start());
	}

	@Override
	public void onEvent(RunnableEvent event) {
		synchronized (LOCk) {
			showEvent(event);
		}
	}

	private void showEvent(RunnableEvent event) {
		System.out.println(
				"The runnable [" + event.getThread().getName() + "] data changed and state is [" + event.getStatus()
						+ "]");

		if (event.getCause() != null) {
			System.out.println("The runnable [" + event.getThread().getName() + "] process failed");
			event.getCause().printStackTrace();
		}
	}
}
