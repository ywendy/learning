package com.ypb.token.entry;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ApiResult<T> {

	private Integer code;
	private String message;
	private T data;

	public ApiResult(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}
