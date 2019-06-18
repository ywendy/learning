package com.ypb.canal.redis.config;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import java.net.InetSocketAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
/**
 * @ClassName: CanalConfig
 * @Description: 配置Canal
 * @author yangpengbing
 * @date 2019-05-31-16:11
 * @version V1.0.0
 *
 */
//@Configuration
public class CanalConfig {

	@Value("${canal.host}")
	private String host;
	@Value("${canal.port}")
	private int port;
	@Value("${canal.destination}")
	private String destination;
	@Value("${canal.username}")
	private String username;
	@Value("${canal.password}")
	private String password;
	@Value("${canal.subscribe}")
	private String subscribe;
	@Value("${canal.zkServers}")
	private String zkServers;

	@Bean
	@Scope("prototype")
	public CanalConnector canalConnector() {
		CanalConnector connector = CanalConnectors.newClusterConnector(zkServers, destination, username, password);
		connector.connect();
		// 指定需要订阅的数据库和表
		connector.subscribe(subscribe);
		// 回滚到上次中断的位置
		connector.rollback();

		return connector;
	}

	private InetSocketAddress initSocketAddress(String host, int port) {
		return new InetSocketAddress(host, port);
	}
}
