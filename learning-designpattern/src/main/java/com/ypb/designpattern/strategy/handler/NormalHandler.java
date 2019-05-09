package com.ypb.designpattern.strategy.handler;

import com.ypb.designpattern.strategy.AbstractHandler;
import com.ypb.designpattern.strategy.HandlerType;
import com.ypb.designpattern.strategy.entity.OrderEntity;
import com.ypb.designpattern.strategy.enums.OrderTypeEnum;
import org.springframework.stereotype.Component;

/**
 * @ClassName: NormalHandler
 * @Description: 普通订单处理器
 * @author yangpengbing
 * @date 2019-05-09-16:58
 * @version V1.0.0
 *
 */
@Component
@HandlerType(OrderTypeEnum.NORMAL)
public class NormalHandler extends AbstractHandler {

	@Override
	public String handle(OrderEntity entity) {
		return "处理普通订单";
	}
}
