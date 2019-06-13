package com.ypb.idempotent;

import com.ypb.idempotent.interceptor.AccessLimitInterceptor;
import com.ypb.idempotent.interceptor.ApiIdempotentInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class ApiIdempotentApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(ApiIdempotentApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
		return new RestTemplate(factory);
	}

	@Bean
	public ClientHttpRequestFactory clientHttpRequestFactory() {
		return new SimpleClientHttpRequestFactory();
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		CorsConfiguration cc = new CorsConfiguration();
		cc.setAllowCredentials(Boolean.TRUE);
		cc.addAllowedOrigin("*");
		cc.addAllowedHeader("*");
		cc.addAllowedMethod("*");

		source.registerCorsConfiguration("/**", cc);

		return new CorsFilter(source);
	}

	/**
	 * 接口幂等性拦截器
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(apiIdempotentInterceptor());
		registry.addInterceptor(accessLimitInterceptor());

		super.addInterceptors(registry);
	}

	@Bean
	public ApiIdempotentInterceptor apiIdempotentInterceptor() {
		return new ApiIdempotentInterceptor();
	}

	@Bean
	public AccessLimitInterceptor accessLimitInterceptor() {
		return new AccessLimitInterceptor();
	}
}
