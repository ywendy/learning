package com.ypb.canal.redis.handler;

import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.Header;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.google.protobuf.InvalidProtocolBufferException;
import com.ypb.canal.redis.service.RedisService;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName: AbstractHandler
 * @Description: 使用责任链模式  链顺序：增->删->改
 * @author yangpengbing
 * @date 2019-05-31-16:49
 * @version V1.0.0
 *
 */
@Slf4j
public abstract class AbstractHandler {

	/**
	 * 处理传递的事件
	 * @param entry
	 */
	public void handlerMessage(Entry entry){
		Header header = entry.getHeader();
		// 发生操作的数据库名
		String database = header.getSchemaName();
		String tableName = header.getTableName();

		log.info("listen database {}, table {}, eventType {}", database, tableName, entry.getEntryType());

		Optional.ofNullable(getRowChange(entry)).ifPresent(rowChange -> handlerRowChange(database, tableName, rowChange));
	}

	/**
	 * 处理数据库 UPDATE、DELETE、INSERT 的数据
	 * @param database
	 * @param tableName
	 * @param rowChange
	 */
	protected abstract void handlerRowChange(String database, String tableName, RowChange rowChange);

	/**
	 * 获得数据库 UPDATE、DELETE、INSERT 的数据
	 * @param entry
	 * @return
	 */
	private RowChange getRowChange(Entry entry) {
		RowChange change = null;
		try {
			change = RowChange.parseFrom(entry.getStoreValue());
		} catch (InvalidProtocolBufferException e) {
			log.debug("get rowchange error by entry {}", entry);
		}

		return change;
	}

	/**
	 * columns 转 map
	 * @return
	 */
	protected Map<String, String> columnsToMap(List<Column> columns) {
		return columns.stream().collect(Collectors.toMap(Column::getName, Column::getValue));
	}
}
