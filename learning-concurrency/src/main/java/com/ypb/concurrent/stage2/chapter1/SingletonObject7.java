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

		Singleton() {
			instance = new SingletonObject7();
		}

		/**
		 * 使用枚举获取到一个对象实例，不需要使用static修饰
		 * @return
		 */
		public SingletonObject7 getInstance() {
			return instance;
		}
	}

    public static SingletonObject7 getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    /**************************** 学习笔记(2019年4月25日) ******************************/
//    枚举类编译后是默认是final class，不允许继承， 枚举类本身也不能继承其他的类，因为枚举类默认继承Enum类，java是单继承， 但是是可以实现接口的
//	  枚举类的构造方法只能用private修饰符修饰
}