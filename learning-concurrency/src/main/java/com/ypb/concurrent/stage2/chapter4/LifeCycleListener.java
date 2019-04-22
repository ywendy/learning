package com.ypb.concurrent.stage2.chapter4;

import com.ypb.concurrent.stage2.chapter4.ObservableRunnable.RunnableEvent;

/**
 * @ClassName: LifeCycleListener
 * @Description: 线程生命周期监听器
 * @author yangpengbing
 * @date 2019-04-22-14:29
 * @version V1.0.0
 *
 */
public interface LifeCycleListener {

	void onEvent(RunnableEvent event);
}
