package com.ypb.canal.redis.handler;

import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.ypb.canal.redis.service.RedisService;
import java.util.List;
import java.util.Map;
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
public class InsertHandler extends AbstractHandler {

	public InsertHandler() {
		this.eventType = EventType.INSERT;
	}

	@Autowired
	private void setNextHandler(DeleteHandler deleteHandler) {
		this.nextHandler = deleteHandler;
	}

	@Autowired
	private RedisService redisService;

	@Override
	protected void handlerRowChange(String database, String tableName, RowChange rowChange) {
		rowChange.getRowDatasList().forEach(rowData -> asyncToRedis(database, tableName, rowData));
	}

	private void asyncToRedis(String database, String tableName, RowData rowData) {
		// 每一行的每列数据  字段名->值
		List<Column> columns = rowData.getAfterColumnsList();
		Map<String, String> map = columnsToMap(columns);

		String scId = map.get("sc_id");
		String key = database + ":" + tableName + ":" + scId;

		redisService.hmset(key, map);
	}
}
