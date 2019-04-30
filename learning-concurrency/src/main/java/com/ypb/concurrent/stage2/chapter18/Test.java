package com.ypb.concurrent.stage2.chapter18;

import com.google.common.collect.Lists;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        ActiveObject activeObject = ActiveObjectFactory.createActiveObject();

	    ResultQueue queue = new ResultQueue();
	    Runnable runnable = new MarkingStringClientRunnable(activeObject, queue);

	    List<Thread> threads = Lists.newArrayList();
        int times = 3;
        for (int i = 0; i < times; i++) {
	        Thread thread = new Thread(runnable, "ypb" + i);

	        threads.add(thread);
        }

	    runnable = new DisplayStringClientRunnable(activeObject, queue);

        times = 1;
        for (int i = 0; i < times; i++) {
	        Thread thread = new Thread(runnable, "bgg" + i);

	        threads.add(thread);
        }

	    threads.forEach(Thread::start);
    }
}
