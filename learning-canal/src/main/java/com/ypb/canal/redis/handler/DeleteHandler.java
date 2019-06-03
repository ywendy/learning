package com.ypb.canal.redis.handler;

import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import org.springframework.stereotype.Component;

@Component
public class DeleteHandler extends AbstractHandler {

	@Override
	protected void handlerRowChange(String database, String tableName, RowChange rowChange) {

	}
}
