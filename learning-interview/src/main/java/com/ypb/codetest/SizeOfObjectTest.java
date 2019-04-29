package com.ypb.codetest;

import java.io.File;

public class SizeOfObjectTest {

	/**
	 * -XX:-UseCompressedOops(关闭指针压缩): sizeOf(new A()) = 24 = 16(header) + 4(instance data) + 4(padding)
	 *      推导过程：new A(), 普通对象引用，没有开启压缩，对象头16个字节，基本数据类型int 4个字节，16+4=20, 不能被8整除，需要padding填充对其，
	 *          而padding的字节数是[0, 8) 3*8 = 24 - 20 = 4，所以padding填充对其的字节数是4
	 * -XX:+UseCompressedOops(开启指针压缩): sizeOf(new A()) = 16 = 12(header) + 4(instance data) + 0(padding)
	 *      推导过程：new A()，普通对象引用， 开启压缩，对象头是12个字节，基本数据类型int 4个字段， 12+4 = 16, 能被8整除，故padding为0
	 */
	private static class A {
		int a;
	}

	/**
	 * -XX:-UseCompressedOops: sizeOf(new B()) = 24 = 16(header) + (4+4)(instance data) + 0(padding)
	 * -XX:+UseCompressedOops: sizeOf(new B()) = 24 = 12(header) + (4+4)(instance data) + 4(padding)
	 * -------------------------------------------------------------------------------------------------
	 * -XX:-UseCompressedOops: sizeOf(new B[3]) = 24(header) + 3*8(instance data) = 24+24 + 0(padding) = 48
	 *      推导过程：new B[3] 未开启压缩：数组对象头(24)，引用类型, 数组长度3(3*8) = 24+24 + 0(padding)
	 * -XX:+UseCompressedOops: sizeOf(new B[3]) = 16(header) + 3*4(instance data) = 16+12 + 4(padding) = 32
	 *      推导过程：new B[3] 开启压缩，数组对象头(16)，引用类型，数组长度为3(4*3) = 16+12 + 4(padding)
	 */
	private static class B {
		int a;
		int b;
	}

	/**
	 * -XX:-UseCompressedOops: sizeOf(new B2()) = 16(header) + (4(int) + 8(Integer))(instance data) = 28 + 4(padding) = 32(4 * 8)
	 *      推导过程：new B2() 未开启指针压缩: 普通对象头(16), 基本数据类型int(4), reference引用类型，未开启压缩(8) = 16 + 4 + 8 = 28 + 4(padding) = 4*8
	 * -XX:+UseCompressedOops: sizeOf(new B2()) = 12(header) + (4 + 4)(instance data) = 20 + 4(padding) = 24(8 * 3)
	 *      推导过程：new B2(), 开启指针压缩：普通对象头(12), 基本数据类型int(4), reference引用类型，开启压缩(4) = 12 + 4 + 4 = 20 + 4(padding) = 3*8;
	 */
	private static class B2 {

		int b2a;
		Integer b2b;
	}

	/**
	 * -XX:-UseCompressedOops: sizeOf(new C()) = 16(header) + (4+8+4)(instance data) = 40 ???
	 *  16(header) + (4(a)+4(ba)+8(as))(instance data) = 16+ 16 = 32 + 0(padding)
	 * // TODO 这个计算和RamUsageEstimator.shallowSizeOf(new C())在相同的情况下计算的结果不一致。 sizeOf计算的是40，另一个计算的是32
	 * -XX:+UseCompressedOops: sizeOf(new C()) = 12(header) + (4+4+4)(instance date) = 24 + 0(padding) = 24
	 */
	private static class C extends A {

		int ba;
		B[] as = new B[3];

		public C() {
			for (int i = 0; i < as.length; i++) {
				as[i] = new B();
			}
		}
	}

	/**
	 * -XX:-UseCompressedOops: sizeOf(new C1()) = 16(header) + (4+8)(instance data) = 28 + 4(padding) = 32
	 * -XX:+UseCompressedOops: sizeOf(new C1()) = 12(header) + (4+4)(instance data) = 20 + 4(padding) = 24
	 */
	private static class C1 {

		int ba;
		B[] bs = new B[3];

		public C1() {
			for (int i = 0; i < bs.length; i++) {
				bs[i] = new B();
			}
		}
	}

	/**
	 * -XX:-UseCompressedOops: sizeOf(new C2()) = 16(header) + (4+8+4+4)(instance data) = 36 + 4(padding) = 40
	 * -XX:+UseCompressedOops: sizeOf(new C2()) = 12(header) + (4+4+4+4)(instance data) = 28 + 4(padding) = 32
	 */
	private static class C2 extends B {

		int ba;
		B[] bs = new B[3];

		public C2() {
			for (int i = 0; i < bs.length; i++) {
				bs[i] = new B();
			}
		}
	}

	/**
	 * -XX:-UseCompressedOops: sizeOf(new C3()) = 16(header) + 8(Instance data) + 0(padding) = 24
	 * -XX:+UseCompressedOops: sizeOf(new C3()) = 12(header) + 4(Instance data) + 0(padding) = 16
	 */
	private static class C3 {

		B[] bs = new B[3];

		public C3() {
			for (int i = 0; i < bs.length; i++) {
				bs[i] = new B();
			}
		}
	}

	/**
	 * -XX:-UseCompressedOops: sizeOf(new D()) = 16(header) + (4+8+4+4)(Instance data) = 36 + 4(padding) = 40
	 * -XX:+UseCompressedOops: sizeOf(new D()) = 12(header) + (4+4+4+4)(Instance data) = 28 + 4(padding) = 32
	 */
	private static class D extends B {

		int da;
		Integer[] di = new Integer[3];
	}

	/**
	 * -XX:-UseCompressedOops: sizeOf(new E()) = 16(header) + (4+4+4)(instance data) = 16+12 + 4(padding) = 32
	 * -XX:+UseCompressedOops: sizeOf(new E()) = 12(header) + (4+4+4)(instance data) = 12+12 + 0(padding) = 24
	 */
	private static class E extends A {

		int ea;
		int eb;
	}

	public static void main(String[] args) throws IllegalAccessException {
//		C c = new C();
//		System.out.println("RamUsageEstimator.shallowSizeOf(new C()) = " + RamUsageEstimator.shallowSizeOf(c));

		System.out.println("SizeOfObject.sizeOf(new C()) = " + SizeOfObject.sizeOf(new C()));
		System.out.println("SizeOfObject.sizeOf(new C1()) = " + SizeOfObject.sizeOf(new C1()));
		System.out.println("SizeOfObject.sizeOf(new C2()) = " + SizeOfObject.sizeOf(new C2()));
		System.out.println("SizeOfObject.sizeOf(new C3()) = " + SizeOfObject.sizeOf(new C3()));

		System.out.println(new File("./target/classes").getAbsolutePath());
		System.out.println("SizeOfObject.sizeOf(new Object()) = " + SizeOfObject.sizeOf(new Object()));
		System.out.println("SizeOfObject.sizeOf(new A()) = " + SizeOfObject.sizeOf(new A()));

		System.out.println("SizeOfObject.sizeOf(new B()) = " + SizeOfObject.sizeOf(new B()));
		System.out.println("SizeOfObject.sizeOf(new B2()) = " + SizeOfObject.sizeOf(new B2()));
		System.out.println("SizeOfObject.sizeOf(new B[3]) = " + SizeOfObject.sizeOf(new B[3]));

		System.out.println("SizeOfObject.sizeOf(new C()) = " + SizeOfObject.sizeOf(new C()));
		System.out.println("SizeOfObject.fullSizeOf(new C()) = " + SizeOfObject.fullSizeOf(new C()));

		System.out.println("SizeOfObject.sizeOf(new D()) = " + SizeOfObject.sizeOf(new D()));
		System.out.println("SizeOfObject.fullSizeOf(new D()) = " + SizeOfObject.fullSizeOf(new D()));

//		-XX:-UseCompressedOops: sizeOf(new int[3]) = 24(header) + (4*3)(instance data) = 24 + 12 + 4(padding) = 40
//		-XX:+UseCompressedOops: sizeOf(new int[3]) = 16(header) + (4*3)(instance data) = 16 + 12 + 4(padding) = 32
		System.out.println("SizeOfObject.sizeOf(new int[3]) = " + SizeOfObject.sizeOf(new int[3]));

//		-XX:-UseCompressedOops: sizeOf(new Integer(1)) = 16(header) + (4)(instance data) = 16 + 4 + 4(padding) = 24
//		-XX:+UseCompressedOops: sizeOf(new Integer(1)) = 12(header) + (4)(instance data) = 12 + 4 + 0(padding) = 16
		System.out.println("SizeOfObject.sizeOf(new Integer(1)) = " + SizeOfObject.sizeOf(new Integer(1)));
//
//		-XX:-UseCompressedOops: sizeOf(new Integer[0]) = 24(header) + (0)(instance data) = 24 + 0 + 0(padding) = 24
//		-XX:+UseCompressedOops: sizeOf(new Integer[0]) = 16(header) + (0)(instance data) = 16 + 0 + 0(padding) = 16
		System.out.println("SizeOfObject.sizeOf(new Integer[0]) = " + SizeOfObject.sizeOf(new Integer[0]));

//		-XX:-UseCompressedOops: sizeOf(new Integer[1]) = 24(header) + (4)(instance data) = 24 + 4 + 4(padding) = 32
//		-XX:+UseCompressedOops: sizeOf(new Integer[1]) = 16(header) + (4)(instance data) = 16 + 4 + 4(padding) = 24
		System.out.println("SizeOfObject.sizeOf(new Integer[1]) = " + SizeOfObject.sizeOf(new Integer[1]));

//		-XX:-UseCompressedOops: sizeOf(new Integer[2]) = 24(header) + (4*2)(instance data) = 24 + 8 + 0(padding) = 32
//		-XX:+UseCompressedOops: sizeOf(new Integer[2]) = 16(header) + (4*2)(instance data) = 16 + 8 + 0(padding) = 24
		System.out.println("SizeOfObject.sizeOf(new Integer[2]) = " + SizeOfObject.sizeOf(new Integer[2]));

//		System.out.println("SizeOfObject.sizeOf(new Integer[3]) = " + SizeOfObject.sizeOf(new Integer[3]));
//		System.out.println("SizeOfObject.sizeOf(new Integer[4]) = " + SizeOfObject.sizeOf(new Integer[4]));

//		-XX:-UseCompressedOops: sizeOf(new A[3]) = 24(header) + (4*3)(instance data) = 24 + 12 + 4(padding) = 40
//		-XX:+UseCompressedOops: sizeOf(new A[3]) = 16(header) + (4*3)(instance data) = 16 + 12 + 4(padding) = 32
		System.out.println("SizeOfObject.sizeOf(new A[3]) = " + SizeOfObject.sizeOf(new A[3]));

		System.out.println("SizeOfObject.sizeOf(new E()) = " + SizeOfObject.sizeOf(new E()));
	}

	/**************************** 学习笔记(2019年4月28日) ******************************/
//	Java对象的内存布局：对象头(header)，实例数据(instance data)和对齐填充(padding)， 不同环境结果可能存在差异，我所在的环境是64为hotspot虚拟机，64位的windows系统

//	对象头： 对象头在32位操作系统上是8bytes，64位操作系统是16bytes  eg: SizeOfObject.sizeOf(new Object());

//	实例数据：
//		基本类型(primitive type)的内存占用如下：
//		+-------------------+--------------------------------------------+------------------------------------+
//	    |   primitive type  |   memory required(bytes字节)(64位无压缩)     |  64位压缩(-XX:+UseCompressedOops)  |
//		+-------------------+--------------------------------------------+------------------------------------+
//		|   boolean         |   1                                        |   1                                |
//		+-------------------+---------------------------------------------------------------------------------+
//	    |   byte            |   1                                        |   1                                |
//	    +-------------------+---------------------------------------------------------------------------------+
//		|   short           |   2                                        |   2                                |
//		+-------------------+---------------------------------------------------------------------------------+
//		|   char            |   2                                        |   2                                |
//	    +-------------------+---------------------------------------------------------------------------------+
//	    |   int             |   4                                        |   4                                |
//		+-------------------+---------------------------------------------------------------------------------+
//		|   float           |   4                                        |   4                                |
//	    +-------------------+---------------------------------------------------------------------------------+
//		|   long            |   8                                        |   8                                |
//	    +-------------------+---------------------------------------------------------------------------------+
//      |   double          |   8                                        |   8                                |
//	    +-------------------+---------------------------------------------------------------------------------+
//	    |   普通对象头       |   16                                       |  12                                |
//		+-------------------+---------------------------------------------------------------------------------+
//		|   数组对象头       |   24                                       |  16                                |
//	    +-------------------+---------------------------------------------------------------------------------+
//		|   reference(引用) |    8                                       |   4                                |
//	    +-----------------------------------------------------------------------------------------------------+

//	    reference类型在32位系统上是4个字节，在64位系统上是8个字节

//	对其填充：
//		hotspot的对其方式为8字节对齐
//		(对象头+实例数据+padding) % 8  = 0 && 0 <= padding < 8

//	计算对象本身占用的大小和对象总空间占用的大小的区别：
//		1. 本身占用的大小，对象中除了基本类型之外，其他类型都按照引用类型来计算，不要计算引用中的对象的大小
//	    2. 总空间占用的大小，要计算对象中每一个对象的大小，引用中的对象也需要计算，在累加活动总空间。

//	对象本身的大小(https://www.cnblogs.com/magialmoon/p/3757767.html)
}
