package com.ypb.designpattern.strategy.enums;

import lombok.Getter;

/**
 * @ClassName: OrderTypeEnum
 * @Description: 定义订单类型的枚举类
 * @author yangpengbing
 * @date 2019-05-09-17:09
 * @version V1.0.0
 *
 */
public enum OrderTypeEnum {

	NORMAL(1, "普通订单"),
	GROUP(2, "团购订单"),
	PROMOTION(3, "促销订单");

	@Getter
	private int type;
	@Getter
	private String typeName;

	OrderTypeEnum(int type, String typeName) {
		this.type = type;
		this.typeName = typeName;
	}

	public static OrderTypeEnum getOrderTypeByType(int type) {
		for (OrderTypeEnum typeEnum : OrderTypeEnum.values()) {
			if (typeEnum.getType() == type) {
				return typeEnum;
			}
		}
		return null;
	}
}
