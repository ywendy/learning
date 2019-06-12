package com.ypb.token;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
/**
 * @ClassName: RepeatSubmitApplication
 * @Description: AOP的方式实现防重复提交
 * @author yangpengbing
 * @date 2019-06-12-12:13
 * @version V1.0.0
 *
 */
@SpringBootApplication
public class RepeatSubmitApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepeatSubmitApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
		return new RestTemplate(factory);
	}

	@Bean
	public ClientHttpRequestFactory clientHttpRequestFactory() {
		return new SimpleClientHttpRequestFactory();
	}
}
