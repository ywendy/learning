package com.ypb.spring.annotation;

import com.ypb.spring.annotation.bean.Persion;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringAnnotationMain {

    public static void main(String[] args) {
        // 传统方式获取Spirng bean
        String configLocation = "applicationContext.xml";
        String beanName = "persion";

        ApplicationContext ctx = new ClassPathXmlApplicationContext(configLocation);

        Persion persion = (Persion) ctx.getBean(beanName);
        System.out.println("persion = " + persion);
    }
}
