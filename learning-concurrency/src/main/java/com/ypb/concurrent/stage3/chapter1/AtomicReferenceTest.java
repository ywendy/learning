package com.ypb.concurrent.stage3.chapter1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className AtomicReferenceTest
 * @description 使一个引用类型的支持原子操作
 * @author yangpengbing
 * @date 23:15 2019/5/14
 * @version 1.0.0
 */
public class AtomicReferenceTest {

    public static void main(String[] args) {
        Simple simple = new Simple("ypb", 11);

        AtomicReference<Simple> ar = new AtomicReference<>(simple);
        System.out.println("ar.get() = " + ar.get());

        boolean success = ar.compareAndSet(simple, new Simple("happy", 32));
        System.out.println("success = " + success);
        System.out.println("ar.get() = " + ar.get());

        final Simple[] simple1 = new Simple[1];
        final AtomicReference<Simple> ref = new AtomicReference<>();

        JButton button = new JButton();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Simple simple2 = new Simple("hhah", 12);

                // 使用数组包装，或封装个Wrap对象
                simple1[0] = simple2;

                // 使用AtomicReference
                ref.set(simple2);
            }
        });
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Simple {
        private String name;
        private int age;
    }
}
