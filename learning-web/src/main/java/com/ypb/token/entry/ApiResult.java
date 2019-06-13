package com.ypb.token.entry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApiResult<T> {

	private Integer code;
	private String message;
	private T data;

	public ApiResult(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}
