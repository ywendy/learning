package com.ypb.concurrent.stage3.chapter1;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AtomicBooleanTest {
    
    @Test
    public void testCreateWithOutArgument(){
        AtomicBoolean ab = new AtomicBoolean();

        assertFalse(ab.get());
    }

    @Test
    public void testCreateWithArgument() {
        AtomicBoolean ab = new AtomicBoolean(true);

        assertTrue(ab.get());
    }

    @Test
    public void testGetAndSet() {
        AtomicBoolean ab = new AtomicBoolean(Boolean.TRUE);
        boolean value = ab.getAndSet(Boolean.FALSE);

        assertTrue(value);
        assertFalse(ab.get());
    }

    @Test
    public void testCompareAndSet(){
        AtomicBoolean ab = new AtomicBoolean(Boolean.TRUE);
        boolean success = ab.compareAndSet(Boolean.TRUE, Boolean.FALSE);

        assertTrue(success);
        assertFalse(ab.get());
    }

    @Test
    public void testCompareAndSetFailed() {
        AtomicBoolean ab = new AtomicBoolean(Boolean.TRUE);
        boolean success = ab.compareAndSet(Boolean.FALSE, Boolean.TRUE);

        assertFalse(success);
        assertTrue(ab.get());
    }
}
