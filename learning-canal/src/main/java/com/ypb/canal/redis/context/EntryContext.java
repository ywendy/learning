package com.ypb.canal.redis.context;

import com.ypb.canal.redis.entry.CustomDbObj;
import java.util.Map;

public class EntryContext {

	private final Map<String, Class> entryMap;

	public EntryContext(Map<String, Class> entryMap) {
		this.entryMap = entryMap;
	}

	public Class<? extends CustomDbObj> getEntryByKey(String key) {
		return entryMap.get(key);
	}
}
