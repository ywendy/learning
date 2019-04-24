package com.ypb.concurrent.stage2.chatper11;

import java.util.stream.IntStream;

public class ContextTest {

    public static void main(String[] args) {
        IntStream.range(1, 5).forEach(i -> new Thread(new ExecutionTask(), "T" + i).start());
    }

    public void test() {
//        new Thread(ExecutionTask::new).start(); 这样编写不执行ExecutionTask的run方法

        Runnable aNew = ExecutionTask::new;

        System.out.println(aNew);

        Runnable task = new ExecutionTask();
        System.out.println(task);

        // com.ypb.concurrent.stage2.chatper11.ContextTest$$Lambda$1/159259014@13a57a3b
        //  com.ypb.concurrent.stage2.chatper11.ExecutionTask@6ee52dcd
    }
}
