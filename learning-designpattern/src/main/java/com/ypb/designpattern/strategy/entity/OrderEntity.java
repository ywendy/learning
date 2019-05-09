package com.ypb.designpattern.strategy.entity;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {

	private String code;
	private BigDecimal price;

	/**
	 * 订单类型(1， 普通订单，2 团购订单，3 促销订单)
	 */
	private int type;
}
