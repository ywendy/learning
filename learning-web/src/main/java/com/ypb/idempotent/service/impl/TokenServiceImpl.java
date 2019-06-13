package com.ypb.idempotent.service.impl;

import com.ypb.idempotent.common.RedisService;
import com.ypb.idempotent.exception.ServiceException;
import com.ypb.idempotent.service.TokenService;
import com.ypb.token.entry.ApiResult;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class TokenServiceImpl implements TokenService {

	private static final String token_name = "token";
	private static final Integer expire_time = 30 * 60;

	@Autowired
	private RedisService redisService;

	@Override
	public ApiResult createToken() {
		String token = randomToken();

		redisService.set(token, token, expire_time);

		return ApiResult.builder().code(HttpStatus.OK.value()).message("成功").data(token).build();
	}


	private String randomToken() {
		String str = UUID.randomUUID().toString();
		return "idempotent:token:" + str;
	}

	@Override
	public void checkToken(HttpServletRequest request) {
		String token = request.getHeader(token_name);
		if (!StringUtils.hasLength(token)) {
			token = request.getParameter(token_name);
		}

		if (!StringUtils.hasLength(token)) {
			throw new ServiceException("token parameter can not empty.");
		}

		if (!redisService.exists(token)) {
			throw new ServiceException("token check fail, please retry!");
		}

		Long count = redisService.del(token);
		if (count <= 0) {
			throw new ServiceException("token check fail, please retry!");
		}
	}


}
