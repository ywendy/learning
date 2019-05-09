package com.ypb.designpattern.strategy;

import com.ypb.designpattern.ApplicationDesignPatternMain;
import com.ypb.designpattern.strategy.entity.OrderEntity;
import com.ypb.designpattern.strategy.enums.OrderTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationDesignPatternMain.class, webEnvironment = WebEnvironment.MOCK)
public class IOrderServiceTest {

	@Autowired
	private IOrderService orderService;

	@Test
	public void handle() {
		OrderEntity entity = OrderEntity.builder().type(OrderTypeEnum.NORMAL.getType()).build();

		System.out.println("orderService.handle(entity) = " + orderService.handle(entity));
	}
}