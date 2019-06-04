package com.ypb.canal.redis.handler;

import com.alibaba.druid.sql.parser.Token;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.Header;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Maps;
import com.google.protobuf.InvalidProtocolBufferException;
import com.ypb.canal.redis.context.EntryContext;
import com.ypb.canal.redis.entry.CustomDbObj;
import com.ypb.canal.redis.utils.PrimaryKeyHandler;
import com.ypb.canal.redis.utils.SpringContextHolder;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.util.CollectionUtils;

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

	private ConcurrentHashMap<String, Map<String, Field>> cachedClzFields = new ConcurrentHashMap<>();

	private DefaultConversionService conversionService = new DefaultConversionService();

	/**
	 * 处理传递的事件
	 * @param entry
	 */
	public void handlerMessage(Entry entry){
		Header header = entry.getHeader();
		// 发生操作的数据库名
		String database = header.getSchemaName();
		String tableName = header.getTableName();

		if (log.isDebugEnabled()) {
			log.debug("listen database {}, table {}, entryType {}, eventType {}", database, tableName,
					entry.getEntryType(), entry.getHeader().getEventType());
		}

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

	TableEntry parseTableEntry(String database, String tableName, List<Column> columns) throws Exception {
		TableEntry entry = new TableEntry();

		Map<String, Object> map = columnsToMap(columns);

		String key = getKey(database, tableName);
		Class<? extends CustomDbObj> dbObj = getEntryByKey(key);
		CustomDbObj obj = convertRowData(columns, dbObj);

		Integer id = (Integer) getPrimaryKeyValue(dbObj, obj);

		entry.setMap(map);
		entry.setDbObj(obj);
		entry.setId(id);

		return entry;
	}

	private Class<? extends CustomDbObj> getEntryByKey(String key) {
		return SpringContextHolder.getBean(EntryContext.class).getEntryByKey(key);
	}

	private Object getPrimaryKeyValue(Class<? extends CustomDbObj> entry, Object obj) {
		return PrimaryKeyHandler.getPrimaryKeyValue(entry, obj);
	}

	private String getKey(String database, String tableName) {
		return database + Token.COLON.name + tableName;
	}

	/**
	 * columns 转 map
	 * @return
	 */
	private Map<String, Object> columnsToMap(List<Column> columns) {
		return columns.stream().collect(Collectors.toMap(this::parseLowerCamel, Column::getValue));
	}

	private <T> T convertRowData(List<Column> columns, Class<T> clazz) throws Exception {
		if (CollectionUtils.isEmpty(columns)) {
			return null;
		}

		Map<String, Field> fields = getClassFields(clazz);
		if (CollectionUtils.isEmpty(fields)) {
			return null;
		}

		T t = clazz.newInstance();
		for (Column column : columns) {
			String name = parseLowerCamel(column.getName());
			String value = column.getValue();

			Field field = fields.get(name);
			field.set(t, parseValue(value, field.getType()));
		}

		return t;
	}

	private Object parseValue(String value, Class<?> type) {
		if (StringUtils.isBlank(value)) {
			return null;
		}

		return conversionService.convert(value, type);
	}

	private <T> Map<String, Field> getClassFields(Class<T> clazz) {
		Map<String, Field> fieldMap = cachedClzFields.get(clazz.getName());
		if (CollectionUtils.isEmpty(fieldMap)) {
			fieldMap = getAllFieldsForBean(clazz);

			cachedClzFields.putIfAbsent(clazz.getName(), fieldMap);
		}

		return fieldMap;
	}

	private <T> Map<String, Field> getAllFieldsForBean(Class<T> clazz) {
		Map<String, Field> map = Maps.newHashMap();

		Class tempClazz = clazz;
		while (tempClazz != null && !Object.class.getName().toLowerCase().equals(tempClazz.getName())) {
			Field[] fields = tempClazz.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(Boolean.TRUE);

				int modifiers = field.getModifiers();
				if (modifiers == Modifier.PUBLIC || modifiers == Modifier.PRIVATE || modifiers == Modifier.PROTECTED) {
					map.put(field.getName(), field);
				}
			}

			tempClazz = tempClazz.getSuperclass();
		}

		return map;
	}


	private String parseLowerCamel(Column column) {
		return parseLowerCamel(column.getName());
	}

	private String parseLowerCamel(String str) {
		return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, str);
	}

	@Data
	class TableEntry {

		private Integer id;
		private CustomDbObj dbObj;
		private Map<String, Object> map;
	}
}
