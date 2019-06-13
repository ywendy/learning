package com.ypb.idempotent.exception;

import com.ypb.token.entry.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName: CustomControllerAdvice
 * @Description: 自定义的ControllerAdvice，用于处理统一的异常
 * @author yangpengbing
 * @date 2019-06-12-16:12
 * @version V1.0.0
 *
 */
@Slf4j
@ControllerAdvice
public class CustomControllerAdvice {

	@ResponseBody
	@ExceptionHandler(ServiceException.class)
	public ApiResult demo(ServiceException exception) {
		return ApiResult.builder().code(exception.getCode()).message(exception.getMsg()).build();
	}
}
