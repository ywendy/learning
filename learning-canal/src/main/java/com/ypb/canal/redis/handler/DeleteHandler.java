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
 * @ClassName: DeleteHandler
 * @Description: 删除
 * @author yangpengbing
 * @date 2019-06-03-16:55
 * @version V1.0.0
 *
 */
@Component
@HandlerType(EventType.DELETE)
@Slf4j
public class DeleteHandler extends AbstractHandler {

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
			List<Column> columns = rowData.getBeforeColumnsList();

			TableEntry entry = parseTableEntry(database, tableName, columns);

			String key = entry.getDbObj().getKey() + Token.COLON.name + entry.getId();

			log.info("delete key {}", key);

			redisService.del(key);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}
	}
}
