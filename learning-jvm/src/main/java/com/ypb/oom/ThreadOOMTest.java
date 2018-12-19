package com.ypb.oom;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @className ThreadOOMTest
 * @description 测试一个线程出现OOM，同进程下的其他线程还能继续运行吗？
 * @author yangpengbing
 * @date 22:12 2018/12/19
 * @version 1.0.0
 */
@Slf4j
public class ThreadOOMTest {

    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:ss:mm");
        int m = 1024 * 1024;
        String name = "oom-thread";
        new Thread(() -> {
            List<byte[]> bytes = new ArrayList<>();
            while (true) {
                byte[] bs = new byte[m];
                bytes.add(bs);

                show(format(formatter));
            }
        }, name).start();

        name = "not-oom-thread";
        new Thread(()->{
            while (true) {
                show(format(formatter));
            }
        }, name).start();
    }

    /**
     * 控制台输出时间和线程名称，并使当前线程休眠1s
     * @param msg
     */
    private static void show(String msg) {
        System.out.println(msg);

        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 格式化输出的信息
     * @param formatter
     * @return
     */
    private static String format(DateTimeFormatter formatter) {
        return String.format("data {%s}, thread {%s}", LocalDateTime.now().format(formatter), Thread.currentThread().getName());
    }
}
