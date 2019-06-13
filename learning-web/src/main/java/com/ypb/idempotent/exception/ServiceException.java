package com.ypb.idempotent.exception;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

public class ServiceException extends RuntimeException {

	@Getter
	@Setter
	private int code;
	@Getter
	@Setter
	private String msg;

	public ServiceException() {}

	public ServiceException(String msg) {
		this(BigDecimal.ONE.intValue(), msg);
	}

	public ServiceException(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

}
