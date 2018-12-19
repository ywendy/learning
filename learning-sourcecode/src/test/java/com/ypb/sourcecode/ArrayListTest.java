package com.ypb.sourcecode;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
/**
 * @className ArrayListTest
 * @description 测试ArrayList
 * @author yangpengbing
 * @date 22:33 2018/12/18
 * @version 1.0.0
 */
@Slf4j
public class ArrayListTest {

	/**
	 * 测试创建ArrayList造成OOM的case
	 * java.lang.OutOfMemoryError: Requested array size exceeds VM limit
	 * @see ArrayList
	 * @throws Exception
	 */
	@Test
	public void testCreateArrayList() throws Exception {
		int capacity = Integer.MAX_VALUE >> 1;

		System.out.println("capacity = " + capacity);

		List<String> strs = new ArrayList<>(capacity);
		String str = "ypb";

		strs.add(str);

		System.out.println("strs = " + strs);
	}
	
	/**
	 * https://blog.csdn.net/east4ming/article/details/80179704
	 * @throws Exception
	 */
	@Test
	public void demo() throws Exception {
		for (int i = 1000; i > 0; i--) {
			try {
				int[] arr = new int[Integer.MAX_VALUE - i];
				System.out.format("Successfully initialized an array with %,d elements .\n", Integer.MAX_VALUE-i);
			} catch (Throwable e) {
				log.debug(e.getMessage(), e);
			}
		}
	}

	/**
	 * 测试ArrayList的最大容量
	 */
	@Test
	public void testArrayListMaxCapacity() throws Exception {
		int initCapacity = 100;
		List<String> strs = new java.util.ArrayList<>(initCapacity);

		String str = "ypb";

		for (int i = 0, times = Integer.MAX_VALUE; i < times; i++) {
			System.out.println("strs.size() = " + strs.size());
			strs.add(str);
		}
	}
	
    @Test
    public void size() throws Exception {
        List<String> strs = new ArrayList<>();
        System.out.println(strs.size());
    }

    @Test
    public void removeIf() throws Exception {
    }

    @Test
    public void replaceAll() throws Exception {
    }

    @Test
    public void sort() throws Exception {
    }

    @Test
    public void forEach() throws Exception {
    }

    @Test
    public void spliterator() throws Exception {
    }

    @Test
    public void stream() throws Exception {
    }

    @Test
    public void parallelStream() throws Exception {
    }

    @Test
    public void get() throws Exception {
    }

    @Test
    public void add() throws Exception {
    }

    @Test
    public void get1() throws Exception {
    }

    @Test
    public void set() throws Exception {
    }

}