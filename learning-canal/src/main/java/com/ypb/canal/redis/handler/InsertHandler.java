package com.ypb.canal.redis.handler;

import com.alibaba.druid.sql.parser.Token;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.ypb.canal.redis.HandlerType;
import com.ypb.canal.redis.service.RedisService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yangpengbing
 * @version V1.0.0
 * @ClassName: InsertHandler
 * @Description: 监听到数据库插入操作的执行逻辑
 * @date 2019-05-31-16:47
 */
@Component
@HandlerType(EventType.INSERT)
@Slf4j
public class InsertHandler extends AbstractHandler {

	@Autowired
	private RedisService redisService;

	@Override
	protected void handlerRowChange(String database, String tableName, RowChange rowChange) {
		asyncHandlerRowChange(database, tableName, rowChange.getRowDatasList());
	}

	private void asyncHandlerRowChange(String database, String tableName, List<RowData> dates) {
		dates.forEach(rowData -> asyncToRedis(database, tableName, rowData));
	}

	private void asyncToRedis(String database, String tableName, RowData rowData) {
		try {
			// 每一行的每列数据  字段名->值
			List<Column> columns = rowData.getAfterColumnsList();
			TableEntry entry = parseTableEntry(database, tableName, columns);

			String key = entry.getDbObj().getKey() + Token.COLON.name + entry.getId();

			log.info("insert key {}", key);

			redisService.hmset(key, entry.getMap());
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}
	}

}
