package com.ypb.token.test;

import com.sun.org.apache.bcel.internal.generic.NEW;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName: RunTest
 * @Description: 多线程ces
 * @author yangpengbing
 * @date 2019-06-12-11:26
 * @version V1.0.0
 *
 */
@Slf4j
@Component
public class RunTest implements ApplicationRunner {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public void run(ApplicationArguments applicationArguments) throws Exception {
		String url = "http://localhost:8000/submit";
		CountDownLatch latch = new CountDownLatch(1);
		ExecutorService pool = Executors.newFixedThreadPool(10);

		for (int i = 0; i < 10; i++) {
			String userId = "userId" + i;

			HttpEntity entity = buildRequest(userId);

			pool.submit(() -> {
				try {
					latch.await();

					log.info("thread: {}, time: {}", Thread.currentThread().getName(), System.currentTimeMillis());

					ResponseEntity<String> post = restTemplate.postForEntity(url, entity, String.class);

					log.info("thread: {}, response: {}", Thread.currentThread().getName(), post.getBody());
				} catch (InterruptedException e) {
					log.debug(e.getMessage(), e);
				}
			});
		}

		latch.countDown();
	}

	private HttpEntity buildRequest(String userId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("token", "youtoken");

		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		return new HttpEntity(map, headers);
	}

}
