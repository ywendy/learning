package com.ypb.canal.redis.config;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.google.common.collect.Lists;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
/**
 * @ClassName: CanalConfig
 * @Description: 配置Canal
 * @author yangpengbing
 * @date 2019-05-31-16:11
 * @version V1.0.0
 *
 */
@Setter
@Slf4j
@Component
@ConfigurationProperties("canal")
public class CanalConfig {

	private String host;
	private int port;
	private String destination;
	private String username;
	private String password;
	private String subscribe;
	private int batchSize;

	@Bean
	public CanalConnector canalConnector() {
		List<SocketAddress> addresses = Lists.newArrayList(initSocketAddress(host, port));

		CanalConnector connector = CanalConnectors.newClusterConnector(addresses, destination, username, password);
		connector.connect();
		// 指定需要订阅的数据库和表
		connector.subscribe(subscribe);
		// 回滚到上次中断的位置
		connector.rollback();
		return connector;
	}

	private SocketAddress initSocketAddress(String host, int port) {
		return new InetSocketAddress(host, port);
	}
}
