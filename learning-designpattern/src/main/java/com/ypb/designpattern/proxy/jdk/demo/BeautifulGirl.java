package com.ypb.designpattern.proxy.jdk.demo;

import java.text.MessageFormat;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yangpengbing
 * @version V1.0.0
 * @ClassName: BeautifulGirl
 * @Description: 定义类BeautifulGirl实现IPerson接口
 * @date 2018/12/14-11:40
 */
@Data
@Slf4j
public class BeautifulGirl implements IPerson {

	private String sex = "女";
	private String name = "白富美";

	@Override
	public void findLove() {
		String pattern = "我叫{0}, 性别{1}, 我找对象的要求是:";
		System.out.println(MessageFormat.format(pattern, name, sex));

		pattern = "高富帅";
		System.out.println(pattern);

		pattern = "有房有车的";
		System.out.println(pattern);

		pattern = "身高要求180cm以上，体重70kg";
		System.out.println(pattern);
	}
}
