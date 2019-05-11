package com.ypb.concurrent.stage1.chapter10;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BooleanLock implements Lock {

    /**
     * true表示已经有线程获取到锁了，需要wait
     * false表示没有获取到锁，可以获取锁
     */
    private boolean locked = false;

    private Collection<Thread> blockedThreads;
    private Thread currentThread;

    public BooleanLock() {
        this.blockedThreads = Lists.newArrayList();
    }

    @Override
    public synchronized void lock() throws InterruptedException {
        while (locked) {
            blockedThreads.add(Thread.currentThread());
            this.wait();
        }

        locked = true;
        currentThread = Thread.currentThread();
        blockedThreads.remove(currentThread);
    }

    @Override
    public synchronized void lock(long mills) throws InterruptedException, TimeOutException {
        if (mills <= 0) {
            lock();
            return;
        }

        long interval = mills;
        long end = System.currentTimeMillis() + mills;

        while (locked) {
            if (interval <=0) {
                throw new TimeOutException(Thread.currentThread().getName() + " time out.");
            }

            blockedThreads.add(Thread.currentThread());
            this.wait(mills);

            interval = end - System.currentTimeMillis();
        }

        locked = true;
        currentThread = Thread.currentThread();
        blockedThreads.remove(currentThread);
    }

    @Override
    public synchronized void unLock() {
        if (currentThread != Thread.currentThread()) {
            return;
        }

        printConsole(Optional.of(Thread.currentThread().getName() + " release the lock monitor.."));
        locked = false;
        this.notifyAll();
    }

    private void printConsole(Optional<String> opt) {
        opt.ifPresent(System.out::println);
    }

    @Override
    public Collection<Thread> getBlockedThreads() {
        return Collections.unmodifiableCollection(blockedThreads);
    }

    @Override
    public int blockedThreads() {
        return blockedThreads.size();
    }
}
