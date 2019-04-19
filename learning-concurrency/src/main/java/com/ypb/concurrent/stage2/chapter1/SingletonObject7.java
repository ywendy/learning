package com.ypb.concurrent.stage2.chapter1;
/**
 * @className SingletonObject7
 * @description 使用枚举创建单例
 * @author yangpengbing
 * @date 22:30 2019/4/19
 * @version 1.0.0
 */
public class SingletonObject7 {

    private SingletonObject7(){}

    private enum Singleton {
        INSTANCE;

        private final SingletonObject7 instance;

        Singleton(){
            instance = new SingletonObject7();
        }

        public SingletonObject7 getInstance() {
            return instance;
        }
    }

    public static SingletonObject7 getInstance() {
        return Singleton.INSTANCE.getInstance();
    }
}
