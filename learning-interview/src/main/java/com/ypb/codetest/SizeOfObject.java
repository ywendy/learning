package com.ypb.codetest;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @ClassName: SizeOfObject
 * @Description: 测试一个对象占用多少字节(对象占用字节大小工具类)
 * @author yangpengbing
 * @date 2019-04-28-10:50
 * @version V1.0.0
 *
 */
public class SizeOfObject {

	static Instrumentation inst;

	static void premain(String args, Instrumentation instP) {
		inst = instP;
	}

	/**
	 * 直接计算当前对象占用空间的大小，包括当前类及超类的基本类型实例字段的大小
	 * 引用类型实例字段引用大小，实例基本类型数组占用空间大小，实例引用类型数组引用本身占用空间大小
	 * 但是不包括超类继承下来的和当前类声明的实例引用字段的对象本身的大小，实例引用数组的对象本身的大小
	 * @param obj
	 * @return
	 */
	public static long sizeOf(Object obj) {
		return inst.getObjectSize(obj);
	}

	public static long fullSizeOf(Object obj) throws IllegalAccessException {
		Set<Object> visited = new HashSet<>();
		Deque<Object> deque = new ArrayDeque<>();
		deque.add(obj);

		long size = 0;
		while (deque.size() > 0) {
			Object o = deque.poll();

			// sizeOf的时候已经计算基本类型和引用的长度，包括数组
			size += skipObject(visited, o) ? 0L : sizeOf(o);

			Class<?> clazz = o.getClass();
			if (clazz.isArray()) {
				// [I, [F 基本类型名字长度是2
				if (clazz.getName().length() > 2) {
					for (int i = 0, len = Array.getLength(o); i < len; i++) {
						Object temp = Array.get(o, i);
						if (Objects.nonNull(temp)) {
							// 非基本类型需要深度遍历其对象
							deque.add(temp);
						}
					}
				}
			} else {
				while (Objects.nonNull(clazz)) {
					Field[] fields = clazz.getDeclaredFields();
					for (Field field : fields) {
						// 静态不计，基本类型不重复计算
						if (Modifier.isStatic(field.getModifiers()) || field.getType().isPrimitive()) {
							continue;
						}

						field.setAccessible(true);
						Object fieldValue = field.get(o);
						if (Objects.isNull(fieldValue)) {
							continue;
						}

						deque.add(fieldValue);
					}

					clazz = clazz.getSuperclass();
				}
			}
		}

		return size;
	}

	/**
	 * String.intern的对象不计，计算过的不计，也避免死循环
	 * @param visited
	 * @param obj
	 * @return
	 */
	private static boolean skipObject(Set<Object> visited, Object obj) {
		if (obj instanceof String && obj == ((String) obj).intern()) {
			return true;
		}

		return visited.contains(obj);
	}
}
