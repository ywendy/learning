package com.ypb.concurrent.stage2.chapter4;

import java.util.Arrays;

public class ThreadLifeCycleClinet {

	public static void main(String[] args) {
		new ThreadLifeCycleObserver().concurrentQuery(Arrays.asList(1, 2));
	}
}
