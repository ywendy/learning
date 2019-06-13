package com.ypb.idempotent.controller;

import com.ypb.token.entry.ApiResult;
import com.ypb.idempotent.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/token")
public class TokenController {

	@Autowired
	private TokenService tokenService;

	@GetMapping
	public ApiResult token(){
		return tokenService.createToken();
	}
}
