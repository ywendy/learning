package com.ypb.idempotent.service;

import com.ypb.token.entry.ApiResult;

public interface TestService {

	ApiResult testIdempotent();

	ApiResult testAccessLimit();
}
