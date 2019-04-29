package com.ypb.codetest.system;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

/**
 * @author yangpengbing
 * @version V1.0.0
 * @ClassName: ArrayCopyTest
 * @Description: 测试System.arrayCopy方法复制数组
 * @date 2019-04-29-10:10
 */
public class ArrayCopyTest {

	public static void main(String[] args) {
		int[] ints = {0, 1, 2, 3, 4, 5, 6, 7};

		printConsole(ints);
		System.arraycopy(ints, 1, ints, 4, 3);

		printConsole(ints);
	}

	/**
	 * 基本类型的数组转list对象，并输出
	 */
	private static void printConsole(int[] ints) {
		List<Integer> integers = Arrays.asList(ArrayUtils.toObject(ints));

		System.out.println(integers);
	}

	/**************************** 学习笔记(2019年4月29日) ******************************/
//  public static native void arraycopy(Object src, int srcPos, Object dest, int destPos, int length);
//	说明：
//		1.此方法为native方法  (http://www.cnblogs.com/Alandre/p/4456719.html)
//			native 即 JNI java平台有个用户和本地C代码进行交互操作的API，称为Java Native Interface(Java本地接口)
//		2.src: 源数组, srcPos:源数组要复制的起始位置, dest: 目的数组, destPos: 目的数组的起始位置, length: 复制的长度
//	注意：src and dest都必须是同种类型或者可以进行类型转换的数组才可以

//	要求：
//		源的起始位置+长度不能超过源数组的长度：也就是srcPos+length <= src源数组的size
//		目标起始位置+长度不能超过目标数组的长度：也就是destPos+length <= desc目标数组的size
}
