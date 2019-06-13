package com.ypb.idempotent.controller;

import com.ypb.idempotent.annoation.AccessLimit;
import com.ypb.idempotent.annoation.ApiIdempotent;
import com.ypb.idempotent.service.TestService;
import com.ypb.token.entry.ApiResult;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	private TestService testService;

	/**
	 * 测试幂等性
	 * @return
	 */
	@ApiIdempotent
	@PostMapping("testIdempotent")
	public ApiResult testIdempotent(){
		return testService.testIdempotent();
	}

	/**
	 * 测试防刷
	 * @return
	 */
	@AccessLimit(maxCount = 500, times = 1, unit = TimeUnit.SECONDS)
	@PostMapping("testAccessLimit")
	public ApiResult testAccessLimit() {
		return testService.testAccessLimit();
	}
}
