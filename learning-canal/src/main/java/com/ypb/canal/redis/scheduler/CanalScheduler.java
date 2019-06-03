package com.ypb.canal.redis.scheduler;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.Message;
import com.ypb.canal.redis.handler.AbstractHandler;
import com.ypb.canal.redis.service.HandlerContext;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Slf4j
@Component
public class CanalScheduler {

	@Resource
	private CanalConnector canalConnector;
	@Value("${canal.batchSize}")
	private int batchSize;
	@Resource
	private HandlerContext handlerContext;

	/**
	 * 异步线程，200毫秒拉取一次数据
	 */
	@Async("canal")
	@Scheduled(fixedDelay = 200)
	public void fetch(){
		try {
			handlerMessage();
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}
	}

	private void handlerMessage() {
		Message message = canalConnector.getWithoutAck(batchSize);
		long batchId  = message.getId();
		log.info("batchId {}", batchId);

		if (batchId == -1 || CollectionUtils.isEmpty(message.getEntries())) {
			canalConnector.ack(batchId);

			return;
		}

		try {
			List<Entry> entries = message.getEntries();
			System.out.println("entries.size() = " + entries.size());
			entries.stream().filter(this::filter).forEach(this::handlerMessage);
		} catch (Exception e) {
			log.debug("batch fetch mysql sync error, batchId {} rollback", batchId);
			canalConnector.rollback(batchId);
		}

		canalConnector.ack(batchId);
	}

	private boolean filter(Entry entry) {
		EntryType entryType = entry.getEntryType();

		if (EntryType.TRANSACTIONBEGIN == entryType || entryType == EntryType.TRANSACTIONEND) {
			return Boolean.FALSE;
		}

		return Boolean.TRUE;
	}

	private void handlerMessage(Entry entry) {
		AbstractHandler handler = handlerContext.getInstance(entry.getHeader().getEventType());
		handler.handlerMessage(entry);
	}
}
