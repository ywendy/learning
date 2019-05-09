package com.ypb.designpattern.strategy.handler;

import com.ypb.designpattern.strategy.AbstractHandler;
import com.ypb.designpattern.strategy.HandlerType;
import com.ypb.designpattern.strategy.entity.OrderEntity;
import com.ypb.designpattern.strategy.enums.OrderTypeEnum;
import org.springframework.stereotype.Component;

@Component
@HandlerType(OrderTypeEnum.GROUP)
public class GroupHandler extends AbstractHandler {

	@Override
	public String handle(OrderEntity entity) {
		return "处理团购订单";
	}
}
