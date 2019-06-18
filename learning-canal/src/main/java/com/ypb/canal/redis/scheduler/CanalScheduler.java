package com.ypb.canal.redis.scheduler;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.Message;
import com.ypb.canal.redis.context.HandlerContext;
import com.ypb.canal.redis.handler.AbstractHandler;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Slf4j
//@Component
public class CanalScheduler {

	@Autowired
	private CanalConnector canalConnector;
	@Value("${canal.batchSize}")
	private int batchSize;
	@Resource
	private HandlerContext handlerContext;

	/**
	 * 异步线程，200毫秒拉取一次数据
	 */
//	@Scheduled(fixedDelay = 100)
	public void fetch(){
		handlerMessage();
	}

	private void handlerMessage() {
		long batchId = 0L;
		try {
			Message message = canalConnector.getWithoutAck(batchSize);
			batchId = message.getId();

			log.info("batchId {}", batchId);

			if (batchId == -1 || CollectionUtils.isEmpty(message.getEntries())) {
				canalConnector.ack(batchId);

				TimeUnit.MILLISECONDS.sleep(200);

				return;
			}

			List<Entry> entries = message.getEntries();
			entries.stream().filter(this::filter).forEach(this::handlerMessage);
			canalConnector.ack(batchId);

		} catch (Exception e) {
			log.debug("batch fetch mysql sync error, batchId {} rollback", batchId);
			if (Objects.nonNull(canalConnector)) {
				canalConnector.rollback(batchId);
			}
		}
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
