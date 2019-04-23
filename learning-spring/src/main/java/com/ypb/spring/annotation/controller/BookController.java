package com.ypb.spring.annotation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin
public class BookController {

}

/**************************** 学习笔记(2019年3月12日) ******************************/
// @Controller注解， 注册到IOC容器中的bean的名称是类名(首字母小写) eg bookController
// @RestController注解，是多个注解合并， bean的名称也是类名(首字母小写)