package com.ypb.concurrent.stage3.chapter1;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicIntegerArray;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AtomicIntegerArrayTest {
    
    @Test
    public void testCreateAtomicIntegerArray(){
        AtomicIntegerArray array = new AtomicIntegerArray(10);
        assertEquals(10, array.length());
    }

    @Test
    public void testGet(){
        AtomicIntegerArray array = new AtomicIntegerArray(10);
        assertEquals(0, array.get(5));
    }
    
    @Test
    public void testSet(){
        AtomicIntegerArray array = new AtomicIntegerArray(10);
        array.set(5, 5);

        assertEquals(5, array.get(5));
    }

    @Test
    public void testGetAndSet(){
        int[] arr = new int[10];
        arr[5] = 5;
        AtomicIntegerArray array = new AtomicIntegerArray(arr);

        int value = array.getAndSet(5, 6);
        assertEquals(value, 5);
        assertEquals(6, array.get(5));
    }
    
    @Test
    public void testCompareAndSet(){
        AtomicIntegerArray array = new AtomicIntegerArray(10);
        array.set(5, 5);

        boolean success = array.compareAndSet(5, 5, 6);
        assertTrue(success);
        assertEquals(6, array.get(5));
    }
}
