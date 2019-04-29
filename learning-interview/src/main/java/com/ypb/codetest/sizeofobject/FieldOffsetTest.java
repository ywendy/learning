package com.ypb.codetest.sizeofobject;

import java.lang.reflect.Field;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.util.RamUsageEstimator;
import sun.misc.Unsafe;

@Slf4j
public class FieldOffsetTest {

	static Unsafe unsafe;
	static {
		try {
			Field field = Unsafe.class.getDeclaredField("theUnsafe");

			field.setAccessible(true);
			unsafe = (Unsafe) field.get(null);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private static class A {
		int a;
	}

	private static class B {
		int a;
		int b;
	}

	private static class C extends A {
		int ba;
		B[] as = new B[3];

		public C() {
			for (int i = 0; i < as.length; i++) {
				as[i] = new B();
			}
		}
	}

	static long objectFieldOffset(Field field) {
		return unsafe.objectFieldOffset(field);
	}

	static String objectFieldOffset(Class<?> clazz) {
		Field[] declaredFields = clazz.getSuperclass().getDeclaredFields();

		Field[] fields = clazz.getDeclaredFields();

		Field[] temps = new Field[fields.length + declaredFields.length];

		System.arraycopy(declaredFields, 0, temps, 0, declaredFields.length);
		System.arraycopy(fields, 0, temps, declaredFields.length, fields.length);

		fields = temps;
		StringBuilder builder = new StringBuilder(fields.length * 50);
		builder.append(clazz.getName()).append(" Field offset:\n");

		for (Field field : fields) {
			builder.append("\t").append(field.getType().getSimpleName());
			builder.append("\t").append(field.getName()).append(": ");
			builder.append(objectFieldOffset(field)).append("\n");
		}

		return builder.toString();
	}

	public static void main(String[] args) {
		System.gc();
		System.out.println(objectFieldOffset(C.class));

		System.out.println(RamUsageEstimator.shallowSizeOf(new C()));
	}
}
