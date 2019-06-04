package com.ypb.canal.redis.config;

import java.math.BigDecimal;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class ThreadPoolConfig {

	@Bean("canal")
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

		int coreSize = Runtime.getRuntime().availableProcessors();
		taskExecutor.setCorePoolSize(coreSize);
		taskExecutor.setMaxPoolSize(coreSize * 2 + 1);
		taskExecutor.setKeepAliveSeconds(BigDecimal.ZERO.intValue());
		taskExecutor.setQueueCapacity(Integer.MAX_VALUE);
		taskExecutor.setThreadNamePrefix("canal-");
		taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

		return taskExecutor;
	}
}
