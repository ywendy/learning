package com.ypb.designpattern.strategy.service;

import com.ypb.designpattern.strategy.AbstractHandler;
import com.ypb.designpattern.strategy.IOrderService;
import com.ypb.designpattern.strategy.entity.OrderEntity;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("orderService")
public class OrderServiceImpl implements IOrderService {

	@Resource
	private HandlerContext handlerContext;

	@Override
	public String handle(OrderEntity entity) {
		AbstractHandler handler = handlerContext.getInstance(entity.getType());
		return handler.handle(entity);
	}
}
