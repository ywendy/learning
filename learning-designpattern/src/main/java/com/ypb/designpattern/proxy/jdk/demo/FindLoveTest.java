package com.ypb.designpattern.proxy.jdk.demo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.misc.ProxyGenerator;

/**
 * @author yangpengbing
 * @version V1.0.0
 * @ClassName: FindLoveTest
 * @Description: 测试FindLove方法
 * @date 2018/12/14-16:03
 */
public class FindLoveTest {

	public static void main(String[] args) throws IOException {
		IPerson person = (IPerson) new Matchmaker().getInstance(new BeautifulGirl());
		person.findLove();

		byte[] bts = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{IPerson.class});

		String path = FindLoveTest.class.getResource("/").getPath() + "$Proxy0.class";
		OutputStream os = new FileOutputStream(path);
		os.write(bts);
		os.close();
	}
}
