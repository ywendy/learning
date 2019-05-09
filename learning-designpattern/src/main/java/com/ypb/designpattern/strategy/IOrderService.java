package com.ypb.designpattern.strategy;

import com.ypb.designpattern.strategy.entity.OrderEntity;

public interface IOrderService {

	/**
	 * 根据订单的不同类型做出不同的处理
	 * @param entity
	 * @return
	 */
	String handle(OrderEntity entity);
}
