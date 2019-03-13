package com.ypb.spring.annotation.config;

import com.ypb.spring.annotation.bean.Persion;
import com.ypb.spring.annotation.service.BookService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;

@ComponentScan(value = "com.ypb.spring.annotation", excludeFilters = {
		@Filter(type = FilterType.ANNOTATION, classes = {Controller.class}),
		@Filter(type = FilterType.ASSIGNABLE_TYPE, classes = BookService.class),
		@Filter(type = FilterType.CUSTOM, classes = CustomTypeFilter.class)
})

/*@ComponentScan(value = "com.ypb.spring.annotation", includeFilters = {
		@Filter(type = FilterType.ANNOTATION, classes = Controller.class),
		@Filter(type = FilterType.ASSIGNABLE_TYPE, classes = BookService.class),
		@Filter(type = FilterType.CUSTOM, classes = CustomTypeFilter.class)
}, useDefaultFilters = false)*/
@Configuration
@ImportResource("classpath:/applicationContext.xml")
public class MainConfig {

	@Bean("person")
	public Persion person(){
		System.out.println("给IOC容器注册persion");
		return new Persion("杨鹏兵", 30);
	}

	@Bean
	public Persion person01(){
		System.out.println("给IOC容器注册persion01");
		return new Persion("ypb", 30);
	}

	@Configuration
	static class config {

		@Bean("persion_hh")
		public Persion persion() {
			return new Persion("hh", 30);
		}
	}
}

