package com.ypb.designpattern.strategy;

import com.ypb.designpattern.strategy.entity.OrderEntity;

public abstract class AbstractHandler {

	public abstract String handle(OrderEntity entity);
}
