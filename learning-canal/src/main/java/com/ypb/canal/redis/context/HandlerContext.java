package com.ypb.canal.redis.context;

import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.ypb.canal.redis.handler.AbstractHandler;
import com.ypb.canal.redis.utils.SpringContextHolder;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HandlerContext {

	private final Map<EventType, Class> handlerMap;

	public HandlerContext(Map<EventType, Class> handlerMap) {
		this.handlerMap = handlerMap;
	}

	public AbstractHandler getInstance(EventType eventType) {
		Class clazz = handlerMap.get(eventType);

		if (Objects.isNull(clazz)) {
			throw new IllegalStateException("not found handler for type " + eventType);
		}

		return (AbstractHandler) SpringContextHolder.getBean(clazz);
	}
}
