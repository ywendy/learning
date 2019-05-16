package com.ypb.concurrent.stage3.chapter1;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class FailedAtomicIntegerFieldUpdaterTest {

    @Test(expected = RuntimeException.class)
    public void testPrivateFieldAccessError(){
        AtomicIntegerFieldUpdater<TestMe> updater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "count");
        TestMe me = new TestMe();

        updater.compareAndSet(me, 0, 1);
    }

    @Test
    public void testTargetObjectIsNull(){
        AtomicIntegerFieldUpdater<TestMe> updater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "count1");
        TestMe me = null;

        updater.compareAndSet(me, 0, 1);
    }

    @Test
    public void testFieldNameInvalid(){
        AtomicIntegerFieldUpdater<TestMe> updater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "counter");
        TestMe me = new TestMe();

        updater.compareAndSet(me, 0, 1);
    }

    @Test
    public void testFieldTypeInvalid() {
        AtomicReferenceFieldUpdater<TestMe, Long> updater = AtomicReferenceFieldUpdater.newUpdater(TestMe.class, Long.class, "count1");
        TestMe me = new TestMe();

        updater.compareAndSet(me, 0L, 1L);
    }

    @Test
    public void testFieldIsNotVolatile(){
        AtomicIntegerFieldUpdater<TestMe> updater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "count2");
        TestMe me = new TestMe();

        updater.compareAndSet(me, 0, 1);
    }
}
