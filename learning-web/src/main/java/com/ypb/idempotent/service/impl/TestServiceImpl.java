package com.ypb.idempotent.service.impl;

import com.ypb.idempotent.service.TestService;
import com.ypb.token.entry.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestServiceImpl implements TestService {

	@Override
	public ApiResult testIdempotent() {
		HttpStatus ok = HttpStatus.OK;

		return ApiResult.builder().code(ok.value()).message(ok.getReasonPhrase()).data("testIdempotent").build();
	}

	@Override
	public ApiResult testAccessLimit() {
		HttpStatus ok = HttpStatus.OK;
		return ApiResult.builder().code(ok.value()).message(ok.getReasonPhrase()).data("testAccessLimit").build();
	}

}
