package com.ypb.canal.redis.config;

import com.alibaba.druid.pool.DruidDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

	@Value("${spring.datasource.url}")
	private String url;
	@Value("${spring.datasource.username}")
	private String username;
	@Value("${spring.datasource.password}")
	private String password;
	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;
	@Value("${spring.datasource.druid.initial-size}")
	private int initialSize;
	@Value("${spring.datasource.druid.min-idle}")
	private int minIdle;
	@Value("${spring.datasource.druid.max-active}")
	private int maxActive;
	@Value("${spring.datasource.druid.max-wait}")
	private int maxWait;
	@Value("${spring.datasource.druid.validation-query}")
	private String validationQuery;
	@Value("${spring.datasource.druid.test-while-idle}")
	private boolean testWhileIdle;
	@Value("${spring.datasource.druid.test-on-borrow}")
	private boolean testOnBorrow;
	@Value("${spring.datasource.druid.test-on-return}")
	private boolean testOnReturn;

	@Bean
	public DataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setDriverClassName(driverClassName);
		dataSource.setInitialSize(initialSize);
		dataSource.setMinIdle(minIdle);
		dataSource.setMaxActive(maxActive);
		dataSource.setMaxWait(maxWait);
		dataSource.setValidationQuery(validationQuery);
		dataSource.setTestWhileIdle(testWhileIdle);
		dataSource.setTestOnBorrow(testOnBorrow);
		dataSource.setTestOnReturn(testOnReturn);

		return dataSource;
	}
}
