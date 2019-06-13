package com.ypb.idempotent.service;

import com.ypb.token.entry.ApiResult;
import javax.servlet.http.HttpServletRequest;

public interface TokenService {

	/**
	 * 检验Token
	 * @param request
	 */
	void checkToken(HttpServletRequest request);

	/**
	 * 生成Token
	 * @return
	 */
	ApiResult createToken();
}
