package com.ypb.jdk8.references;

import java.util.function.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: ConstructorReference
 * @Description: 构造器引用
 * @author yangpengbing
 * @date 2019-05-29-10:01
 * @version V1.0.0
 *
 */
public class ConstructorReference {

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	private static class Person {

		private String firstName;
		private String lastName;
		private int age;
	}

	interface PersonFactory<T extends Person> {

		T create(String first, String lastName, int age);

	}

	public static void main(String[] args) {
		Supplier<Person> supplier = Person::new;

		PersonFactory<Person> factory = Person::new;
		Person person = factory.create("111", "222", 23);

		person = supplier.get();
	}
}
