package com.ypb.spring.annotation.service.impl;

import com.ypb.spring.annotation.dao.BookDao;
import com.ypb.spring.annotation.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookDao bookDao;

	public void addBook(){
		bookDao.addBook();
	}

	@Override
	public BookDao getBookDao() {
		return bookDao;
	}
}
