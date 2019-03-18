package com.ypb.spring.annotation.config;

import com.ypb.spring.annotation.bean.ColorFactoryBean;
import com.ypb.spring.annotation.bean.Persion;
import com.ypb.spring.annotation.bean.Red;
import com.ypb.spring.annotation.condition.CustomImportBeanDefinitionRegistrar;
import com.ypb.spring.annotation.condition.CustomImportSelector;
import com.ypb.spring.annotation.condition.LinuxCondition;
import com.ypb.spring.annotation.condition.WindowsCondition;
import com.ypb.spring.annotation.service.BookService;
import com.ypb.spring.annotation.service.impl.BookServiceImpl;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

@Conditional(WindowsCondition.class)
@Configuration
@Import({Red.class, CustomImportSelector.class, CustomImportBeanDefinitionRegistrar.class})
public class MainConfig2 {

	/**
	 * 使用Spring提供的FactoryBean注册组件
	 * 1. 默认返回的是getObject方法创建的对象，不是FactoryBean。
	 * 2. 如果想获取FactoryBean对象本身，需要在获取的id前添加&
	 * @return
	 */
	@Bean
	public FactoryBean colorFactoryBean() {
		System.out.println("给容器中添加FactoryBean...");
		return new ColorFactoryBean();
	}

	@Bean
	@Conditional(WindowsCondition.class)
	public Persion persion01(){
		System.out.println("给容器中添加Persion...Bill");

		return new Persion("Bill", 62);
	}

	@Bean
	@Lazy
	@Conditional(LinuxCondition.class)
	public Persion persion02(){
		System.out.println("给容器中添加Persion...linus");
		return new Persion("linus", 48);
	}

	@Bean("persion")
	@Scope("prototype")
	public Persion persion(){
		System.out.println("给容器中添加Persion...");

		return new Persion("ypb1", 30);
	}

	/*@Bean("persion")
	@Lazy
	public Persion persion() {
		System.out.println("给容器中添加Persion...");

		return new Persion("ypb", 30);
	}*/
}
