package com.ypb.jmh;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @ClassName: JMHSampleHelloWorld
 * @Description: jmh的测试hello world
 * @author yangpengbing
 * @date 2019-05-20-10:58
 * @version V1.0.0
 *
 */
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
public class JMHSampleHelloWorld {

	static class Demo {

		int id;
		String name;

		public Demo(int id, String name) {
			this.id = id;
			this.name = name;
		}
	}

	static List<Demo> demos;
	static {
		demos = Lists.newArrayList();
		int times = 10000000;
		for (int i = 0; i < times; i++) {
			demos.add(new Demo(i, "ypb" + i));
		}
	}

	/**
	 * 定义基准测试的方法，该基准测试方法执行流程是：每个方法执行都进行5次预热执行，每隔1秒进行一次预热操作，预热执行之后进程5次实际测量执行，每隔1秒进行一次实际执行，我们此次基准测量的是平均响应时长，单位是us(微妙)
	 */
	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	public void testHashMapWithoutSize(){
		Map<Integer, String> map = Maps.newHashMap();
		for (Demo demo : demos) {
			map.put(demo.id, demo.name);
		}
	}

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	public void testHashMap(){
		Map<Integer, String> map = Maps.newHashMapWithExpectedSize(demos.size());
		for (Demo demo : demos) {
			map.put(demo.id, demo.name);
		}
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(JMHSampleHelloWorld.class.getSimpleName()).forks(1).build();

		new Runner(opt).run();
	}

	/**************************** 学习笔记(2019年5月20日) ******************************/
//  为什么要预热，因为JVM的jit机制的存在，如果某个函数被调用多次之后，JVM会尝试将其编译成机器码从而提高执行速度，为了让benchmark的结果更加接近真实的情况就需要进行预热。

//	介绍主要的一些注解的使用：
//	@Benchmark：@Benchmark标签是用来标记测试方法的，只要被这个注解标记的话，该方法才能参与基准测试，但是有一个基本的原则就是被@Beanmark标记的方法必须是public的
//	@Warmup：@Warmup用来配置预热的内容，可用于类或者方法上，越靠近执行的方法的地方越准确，一般配置warmup的参数有这些：
//		1. iterations：预热的次数
//	    2. times：每次预热的时间
//		3. timeUnit：时间单位，默认是秒
//	    4. batchSize：批处理大小，每次操作调用n次方法

//	@Measurement：用来控制实际执行的内容，配置的选项和warmup一样。
//	@BenchmarkMode：@BenchmarkMode主要是表示测量的纬度，有以下这些纬度可供选择：
//		1. Mode.Through 吞吐量纬度
//	    2. Mode.AverageTime 平均时间
//	    3. Mode.SampleTime 抽样检查
//	    4. Mode.SingleShotTime 检测一次调用
//		5. Mode.All 运用所有的检测模式， 在方法方法级别指定@BenchmarkMode的时候可以指定多个纬度(@BenchmarkMode{Mode.Through, Mode.AverageTime, Mode.SampleTime, Mode.SingleShotTime}), 代表同时在多个纬度对目标方法进行测量

//	@OutputTimeUnit：@OutputTimeUnit代表测量的单位，比如秒级别，毫秒级别，微妙级别等待，一般使用微妙和毫秒级别的比较多，该注解可以用在方法级别和类级别，当用在类级别的时候会被更加精确的方法级别的注解覆盖，原则就是离目标更近的注解更容易生效。

//	@State：在很多时候我们需要维护一些状态内容，比如在多线程的时候我们会维护一个共享的状态，这个状态值可能会在每个线程中都一样，也有可能是每个线程都有自己的状态，JMH为我们提供了状态的支持，该注解只能用来标注在类上，因为类作为一个属性的载体，@State的状态主要有以下几种：
//		1. Scope.Benchmark 该状态的意思是会在所有的Benchmark的工作线程中共享变量内容。
//		2. Scope.Group 同一个Group的线程可以享有同样的变量
//	    3. Scope.Thread 每隔线程都享有一份变量的副本，线程之间对于变量的修改不会相互影响。

//	我们试想以下@State的含义，它主要是方便框架来控制变量的过程逻辑，通过@State标示的类都被用作属性的容器，然后框架可以通过自己的控制来配置不同级别的隔离情况。被@Benchmark标注的方法可以有参数，但是参数必须是被@State注解的，就是为了要控制参数的隔离。

//	但是有些情况下我们需要对参数进行一些初始化或者释放的操作，就像Spring提供的一些init和destory方法一样，JHM也提供有这样的钩子：
//	@Setup 必须标示在@State注解的类内部，表示初始化操作

//	@TearDown 必须表示在@State注解的类内部，表示销毁操作
//	初始化和销毁的动作都只会执行一次。

//  虽然我们可以执行初始化和销毁的动作，但是总是感觉还缺点啥？对，就是初始化的粒度。因为基准测试往往会执行多次，那么能不能保证每次执行方法的时候都初始化一次变量呢？ @Setup和@TearDown提供了以下三种纬度的控制：
//	    Level.Trial 只会在个基础测试的前后执行。包括Warmup和Measurement阶段，一共只会执行一次。
//	    Level.Iteration 每次执行记住测试方法的时候都会执行，如果Warmup和Measurement都配置了2次执行的话，那么@Setup和@TearDown配置的方法的执行次数就4次。

//	    Level.Invocation 每个方法执行的前后执行（一般不推荐这么用）

//	@Param: 在很多情况下，我们需要测试不同的参数的不同结果，但是测试的了逻辑又都是一样的，因此如果我们编写镀铬benchmark的话会造成逻辑的冗余，幸好JMH提供了@Param参数来帮助我们处理这个事情，被@Param注解标示的参数组会一次被benchmark消费到。

//  @Threads: 测试线程的数量，可以配置在方法或者类上，代表执行测试的线程数量。
//	通常看到这里我们会比较迷惑Iteration和Invocation区别，我们在配置Warmup的时候默认的时间是的1s，即1s的执行作为一个Iteration，假设每次方法的执行是100ms的话，那么1个Iteration就代表10个Invocation。

//	JMH进阶
//
//  通过以上的内容我们已经基本可以掌握JMH的使用了，下面就主要介绍一下JMH提供的一些高级特性了。
//  不要编写无用代码
//
//  因为现代的编译器非常聪明，如果我们在代码使用了没有用处的变量的话，就容易被编译器优化掉，这就会导致实际的测量结果可能不准确，因为我们要在测量的方法中避免使用void方法，然后记得在测量的结束位置返回结果。这么做的目的很明确，就是为了与编译器斗智斗勇，让编译器不要改变这段代码执行的初衷。
//  Blackhole介绍
//
//  Blackhole会消费传进来的值，不提供任何信息来确定这些值是否在之后被实际使用。 Blackhole处理的事情主要有以下几种：
//
//    死代码消除：入参应该在每次都被用到，因此编译器就不会把这些参数优化为常量或者在计算的过程中对他们进行其他优化。
//
//    处理内存壁：我们需要尽可能减少写的量，因为它会干扰缓存，污染写缓冲区等。 这很可能导致过早地撞到内存壁
//
//  我们在上面说到需要消除无用代码，那么其中一种方式就是通过Blackhole，我们可以用Blackhole来消费这些返回的结果。

//	循环处理

//	我们虽然可以在Benchmark中定义循环逻辑，但是这么做其实是不合适的，因为编译器可能会将我们的循环进行展开或者做一些其他方面的循环优化，所以JHM建议我们不要在Beanchmark中使用循环，如果我们需要处理循环逻辑了，可以结合@BenchmarkMode(Mode.SingleShotTime)和@Measurement(batchSize = N)来达到同样的效果.

//	方法内联
//	    方法内联：如果JVM监测到一些小方法被频繁的执行，它会把方法的调用替换成方法体本身。比如说下面这个：
//		JMH提供了CompilerControl注解来控制方法内联，但是实际上我感觉比较有用的就是两个了：
//			CompilerControl.Mode.DONT_INLINE：强制限制不能使用内联
//	        CompilerControl.Mode.INLINE：强制使用内联 看一下官方提供的例子把：
}
