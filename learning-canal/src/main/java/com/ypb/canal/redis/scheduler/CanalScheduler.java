package com.ypb.canal.redis.scheduler;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.Message;
import com.ypb.canal.redis.handler.InsertHandler;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CanalScheduler {

	@Autowired
	private CanalConnector canalConnector;
	@Value("${canal.batchSize}")
	private int batchSize;
	@Autowired
	private InsertHandler insertHandler;

	/**
	 * 异步线程，200毫秒拉取一次数据
	 */
	@Async("canal")
	@Scheduled(fixedDelay = 200)
	public void fetch(){
		Message message = canalConnector.getWithoutAck(batchSize);
		long batchId  = message.getId();
		log.info("batchId {}", batchId);

		if (batchId < 0) {
			canalConnector.ack(batchId);
			return;
		}

		try {
			List<Entry> entries = message.getEntries();
			entries.forEach(this::handlerMessage);
			canalConnector.ack(batchId);
		} catch (Exception e) {
			log.debug("batch fetch mysql sync error, batchId {} rollback", batchId);
			canalConnector.rollback(batchId);
		}
	}

	private void handlerMessage(Entry entry) {
		if (EntryType.ROWDATA == entry.getEntryType()) {
			insertHandler.handlerMessage(entry);
		}
	}
}
