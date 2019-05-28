package com.ypb.jdk8;

import com.google.common.collect.Lists;
import com.ypb.jdk8.mode.Person;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class TestListStream {

	private static final Random random = new Random(System.currentTimeMillis());
	private static final List<String> birthDays;

	static {
		birthDays = Lists.newArrayList();

		int times = 100;
		for (int i = 0; i < times; i++) {
			int r = random.nextInt(Integer.MAX_VALUE);

			String birthday = initBirthday(r);

			birthDays.add(birthday);
		}
	}

	private static String initBirthday(int r) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		return LocalDateTime.now().plusSeconds(BigDecimal.ZERO.intValue() - r).format(formatter);
	}

	public static void main(String[] args) {
		testMax();
	}

	private static void testMax() {
		int size = 10;
		List<Person> persons = initPersons(size);

		System.out.println("persons = " + persons);

		persons.stream().max(Comparator.comparing(Person::getBirthDay)).ifPresent(System.out::println);
	}

	private static List<Person> initPersons(int size) {
		List<Person> people = Lists.newArrayListWithCapacity(size);
		for (int i = 0; i < size; i++) {
			Person p = initPerson(i, random.nextInt(100));

			people.add(p);
		}
		return people;
	}

	private static Person initPerson(int suffix, int age) {
		Person person = new Person();
		person.setAge(age);
		person.setName("ypb" + suffix);
		person.setBirthDay(getBirthDay(suffix));

		return person;
	}

	private static String getBirthDay(int suffix) {
		suffix = suffix % birthDays.size();

		return birthDays.get(suffix);
	}
}
