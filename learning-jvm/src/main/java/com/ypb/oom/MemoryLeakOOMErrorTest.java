package com.ypb.oom;

import com.google.common.collect.Maps;
import java.util.Map;

/**
 * @author yangpengbing
 * @version 1.0.0
 * @className MemoryLeakOOMErrorTest
 * @description 内存泄漏导致的内存溢出(-Xmx256m -Xms256m -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -verbose:gc -Xloggc:E:/gc.log -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=E:/dump.hprof)
 * @date 17:47 2018/12/23
 */
public class MemoryLeakOOMErrorTest {

	public static void main(String[] args) {
		Map<Key, String> map = Maps.newHashMap();
		while (map.size() >= 0) {
			for (int i = 0; i < 10000; i++) {
				Key key = new Key(i);
				if (map.containsKey(key)) {
					continue;
				}

				map.put(key, "number: " + i);
			}
		}
	}

	private static class Key {

		private final Integer id;

		public Key(Integer id) {
			this.id = id;
		}

		@Override
		public int hashCode() {
			return id.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			boolean flag = Boolean.FALSE;
			if (obj instanceof Key) {
				flag = ((Key) obj).id.equals(this.id);
			}

			return flag;
		}
	}
}
