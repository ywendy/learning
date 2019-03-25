package com.ypb.spring.annotation.config;

import com.ypb.spring.annotation.bean.Persion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @ClassName: MainConfigOfPropertyValues
 * @Description: Spring属性赋值的配置类
 * @author yangpengbing
 * @date 2019/3/21-11:38
 * @version V1.0.0
 *
 */
@Configuration
@PropertySource(value = "classpath:/persion.properties", encoding = "utf-8")
public class MainConfigOfPropertyValues {

	@Bean
	public Persion persion() {
		return new Persion();
	}
}

/**************************** 学习笔记(2019年3月25日) ******************************/
// 1. 使用PropertySource读取外部配置中的k/v保存到运行的环境变量中，加载完外部的的配置文件以后就可以使用${}读取
// 2. 使用@Value赋值：
//	  1）：基本数组
//	  2）：可以使用SpEL表达式， #{}
//    3）：可以写${},取出配置文件中的值(在运行环境变量里面的值)