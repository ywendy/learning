package com.ypb.spring.annotation.config;

import com.ypb.spring.annotation.bean.Book;
import com.ypb.spring.annotation.dao.BookDao;
import com.ypb.spring.annotation.service.BookService;
import java.util.Objects;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainConfigOfAutowiredTest {

	@Test
	public void test01() {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfigOfAutowired.class);
		Book book = ctx.getBean(Book.class);

		System.out.println("book = " + book);

		BookService bookService = (BookService) ctx.getBean("bookServiceImpl");
//		BookDao bookDao = (BookDao) ctx.getBean("bookDaoImpl");

		BookDao bookDao = null;

		System.out.println("bookService.getBookDao() == bookDao = " + (bookService.getBookDao() == bookDao));
	}
}