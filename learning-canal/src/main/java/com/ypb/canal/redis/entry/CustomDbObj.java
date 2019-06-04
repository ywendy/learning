package com.ypb.canal.redis.entry;

import com.alibaba.druid.sql.parser.Token;
import lombok.Getter;

/**
 * @ClassName: CustomDbObj
 * @Description: 自定义数据库对象
 * @author yangpengbing
 * @date 2019-06-04-10:56
 * @version V1.0.0
 *
 */
public class CustomDbObj {

	/**
	 * 数据库名
	 */
	@Getter
	private String database;
	/**
	 * 表名称
	 */
	@Getter
	private String tableName;
	@Getter
	private String key;

	public CustomDbObj(String database, String tableName) {
		this.database = database;
		this.tableName = tableName;

		key = database + Token.COLON.name + tableName;
	}
}
