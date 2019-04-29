package com.ypb.codetest.trywithresources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: TryWithResourcesTest
 * @Description: Java 7中的Try-with-resources测试，是java7中一个新的异常处理机制，它能够h很容易地关闭在try-catch语句块中使用的资源
 * @author yangpengbing
 * @date 2019-04-29-11:10
 * @version V1.0.0
 *
 */
@Slf4j
public class TryWithResourcesTest {

	public static void main(String[] args) throws Exception {
		String filePath = "E:\\workspace\\banner.txt";

		printFile(filePath);
		printFileJava7(filePath);

		customAutoClosable();
	}

	/**
	 * 自定义类型，实现自动关闭
	 */
	private static void customAutoClosable() throws Exception {
		try (CustomAutoClosable cac = new CustomAutoClosable()){
			cac.doIt();
		}
	}

	private static void printFile(String filePath) throws IOException {
		InputStream fis = null;
		try {
			fis = new FileInputStream(filePath);

			int data = fis.read();
			while (data != -1) {
				System.out.println((char) data);

				data = fis.read();
			}
		} catch (FileNotFoundException e) {
			log.debug(e.getMessage(), e);
		}finally {
			fis.close();
		}
	}

	private static void printFileJava7(String filePath) {
		try (InputStream is = new FileInputStream(filePath)) {

			int data = is.read();
			while (data != -1) {
				System.out.println((char) data);

				data = is.read();
			}
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}
	}

	private static class CustomAutoClosable implements AutoCloseable {

		public void doIt(){
			System.out.println("CustomAutoClosable doing it!");
		}

		@Override
		public void close() throws Exception {
			System.out.println("CustomAutoClosable closed!");
		}
	}
}
