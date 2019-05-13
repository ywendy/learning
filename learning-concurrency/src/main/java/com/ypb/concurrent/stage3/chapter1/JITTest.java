package com.ypb.concurrent.stage3.chapter1;

public class JITTest {

    private static boolean init = false;

    public static void main(String[] args) throws InterruptedException {
        new Thread(()-> {
            // JIT会将其翻译成 while(true) {}
//            while (!init) {
//            }

            // JIT不会将其编译成上面的那个，因为语句块中有可能存在对init的变量操作
            while (!init) {
                System.out.println(".");
            }
        }).start();

        Thread.sleep(1000);

        new Thread(()->{
            init = true;
            System.out.println("set init to true..");
        }).start();
    }
}
