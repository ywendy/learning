package com.ypb.codetest.nio;

import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MappedByteBufferTest {

	public static void main(String[] args) {
		rwTest();
	}

	private static void rwTest() {
		int length = 0x8FFFFFF;

		System.out.println(length);

		try (
				FileChannel channel = FileChannel
						.open(Paths.get("D:\\test1.txt"), StandardOpenOption.READ, StandardOpenOption.WRITE)
		) {
			MappedByteBuffer buffer = channel.map(MapMode.READ_WRITE, 0, length);
			for (int i = 0; i < length; i++) {
				buffer.put((byte) 0);
			}

			for (int i = length / 2; i < length / 2 + 4; i++) {
				System.out.println(buffer.get(i));
			}

		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}
	}

	/**************************** 学习笔记(2019年4月28日) ******************************/
//
}
