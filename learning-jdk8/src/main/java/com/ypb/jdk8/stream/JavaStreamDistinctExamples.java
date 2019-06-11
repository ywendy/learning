package com.ypb.jdk8.stream;

import com.google.common.collect.Lists;
import com.ypb.jdk8.mode.Person;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName: JavaStreamDistinctExamples
 * @Description: JDK8去重
 * @author yangpengbing
 * @date 2019-06-11-16:32
 * @version V1.0.0
 *
 */
public class JavaStreamDistinctExamples {

	public static void main(String[] args) {
//		test1();

//		test2();

//		test3();

		test4();
	}

	private static void test4() {
		List<Person> people = initPerson();
		System.out.println("people = " + people);

		people = people.stream().collect(Collectors.collectingAndThen(
				Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Person::getBirthDay))),
				ArrayList::new));
		System.out.println("people = " + people);
	}

	private static void test3() {
		List<Person> people = initPerson();
		System.out.println("people.size() = " + people.size());

		people = people.stream().filter(distinctByKey(Person::getBirthDay)).collect(Collectors.toList());
		System.out.println("people.size() = " + people.size());
	}

	private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> map = new ConcurrentHashMap<>();

		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	private static void test2() {
		List<Person> persones = initPerson();
		System.out.println("persones.size() = " + persones.size());

		persones = persones.stream().distinct().collect(Collectors.toList());
		System.out.println("persones.size() = " + persones.size());
	}

	private static List<Person> initPerson() {
		Person p1 = Person.builder().age(1).birthDay("12-12").name("ypb1").build();
		Person p2 = Person.builder().age(2).birthDay("12-12").name("ypb2").build();
		Person p3 = Person.builder().age(3).birthDay("12-12").name("ypb3").build();

		return Lists.newArrayList(p1, p1, p2, p3, p2, p1, p3);
	}

	private static void test1() {
		System.out.println(Stream.of(1, 2, 3, 1, 2).distinct().collect(Collectors.toList()));
	}
}
