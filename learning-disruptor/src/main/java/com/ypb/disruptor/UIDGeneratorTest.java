package com.ypb.disruptor;

import com.baidu.fsg.uid.UidGenerator;
import com.baidu.fsg.uid.impl.CachedUidGenerator;
import com.baidu.fsg.uid.impl.DefaultUidGenerator;

public class UIDGeneratorTest {

	public static void main(String[] args) {
		UidGenerator generator = new CachedUidGenerator();

		System.out.println("generator.getUID() = " + generator.getUID());
	}

}
