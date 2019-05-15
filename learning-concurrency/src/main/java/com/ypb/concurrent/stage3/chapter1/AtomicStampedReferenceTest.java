package com.ypb.concurrent.stage3.chapter1;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yangpengbing
 * @version 1.0.0
 * @className AtomicStampedReferenceTest
 * @description 解决Atomic的ABA问题。类似于mysql的乐观锁
 * @date 23:32 2019/5/14
 */
@Slf4j
public class AtomicStampedReferenceTest {

    private static final AtomicStampedReference<Integer> ref = new AtomicStampedReference<>(100, 0);

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                boolean success = ref.compareAndSet(100, 101, ref.getStamp(), ref.getStamp() + 1);
                System.out.println("success = " + success);

                success = ref.compareAndSet(101, 100, ref.getStamp(), ref.getStamp() + 1);
                System.out.println("success = " + success);
            } catch (InterruptedException e) {
                log.debug(e.getMessage(), e);
            }
        });

        Thread t2 = new Thread(() -> {
            int stamp = ref.getStamp();
            System.out.println("Before sleep:stamp= " + stamp);
            try {
                TimeUnit.SECONDS.sleep(2);
                boolean success = ref.compareAndSet(100, 101, stamp, stamp + 1);

                System.out.println("success = " + success);
            } catch (InterruptedException e) {
                log.debug(e.getMessage(), e);
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

    }
}
