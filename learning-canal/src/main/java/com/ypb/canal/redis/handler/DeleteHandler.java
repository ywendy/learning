package com.ypb.canal.redis.handler;

import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.ypb.canal.redis.HandlerType;
import com.ypb.canal.redis.service.RedisService;
import java.util.List;
import java.util.Map;
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
public class DeleteHandler extends AbstractHandler {

	@Autowired
	private RedisService redisService;

	@Override
	protected void handlerRowChange(String database, String tableName, RowChange rowChange) {
		rowChange.getRowDatasList().forEach(rowData -> asyncToRedis(database, tableName, rowData));
	}

	private void asyncToRedis(String database, String tableName, RowData rowData) {
		// 每一行的每列数据  字段名->值
		List<Column> columns = rowData.getBeforeColumnsList();
		Map<String, String> map = columnsToMap(columns);

		String cId = map.get("c_id");
		String key = database + ":" + tableName + ":" + cId;

		redisService.del(key);
	}
}
