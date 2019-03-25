package com.ypb.spring.annotation.config;

import com.ypb.spring.annotation.bean.Book;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * @ClassName: MainConfigOfAutowired
 * @Description: spring的自动装配
 * @author yangpengbing
 * @date 2019-03-25-11:17
 * @version V1.0.0
 *
 */
@Configuration
@ComponentScans({
		@ComponentScan(value = "com.ypb.spring.annotation.bean", useDefaultFilters = false, includeFilters = {
				@Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
						Book.class
				} )
		}),
		@ComponentScan(value = {"com.ypb.spring.annotation.service", "com.ypb.spring.annotation.dao"})
})
public class MainConfigOfAutowired {

	@Bean
	public Book book(){
		return new Book();
	}
}

/**************************** 学习笔记(2019年3月25日) ******************************/
// 1. 自动装配：
//    Spring利用依赖注入(DI)，完成对IOC容器中各个组件的依赖关系赋值；
// @Autowired：自动注入
//    1）：默认是按照类型去容器中找对应的组件，找到就赋值