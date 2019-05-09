package com.ypb.designpattern.strategy.service;

import com.ypb.designpattern.strategy.AbstractHandler;
import com.ypb.designpattern.strategy.enums.OrderTypeEnum;
import com.ypb.designpattern.strategy.utils.SpringContextHolder;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HandlerContext {

	private final Map<OrderTypeEnum, Class> handlerMap;

	public HandlerContext(Map<OrderTypeEnum, Class> handlerMap) {
		this.handlerMap = handlerMap;
	}

	public AbstractHandler getInstance(int type) {
		OrderTypeEnum typeEnum = OrderTypeEnum.getOrderTypeByType(type);

		Class clazz = handlerMap.get(typeEnum);
		if (Objects.isNull(clazz)) {
			throw new IllegalStateException("not found handler for type " + type);
		}

		return (AbstractHandler) SpringContextHolder.getBean(clazz);
	}
}
