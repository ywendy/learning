package com.ypb.idempotent.test;

import com.ypb.token.entry.ApiResult;
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

@Slf4j
@Component
public class ApiIdempotentTest implements ApplicationRunner {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public void run(ApplicationArguments applicationArguments) throws Exception {
		String url = "http://localhost:8000/test/testIdempotent";

		url = "http://localhost:8000/test/testAccessLimit";

		CountDownLatch latch = new CountDownLatch(1);
		ExecutorService pool = Executors.newFixedThreadPool(20);

		for (int i = 0; i < 1000; i++) {
			HttpEntity entity = buildRequest();

			String finalUrl = url;
			pool.submit(() -> {
				try {
					latch.await();

					log.info("thread: {}, time: {}", Thread.currentThread().getName(), System.currentTimeMillis());

					ResponseEntity<ApiResult> post = restTemplate.postForEntity(finalUrl, entity, ApiResult.class);

					log.info("thread: {}, response: {}", Thread.currentThread().getName(), post.getBody());
				} catch (InterruptedException e) {
					log.debug(e.getMessage(), e);
				}
			});
		}

		latch.countDown();
	}

	private HttpEntity buildRequest() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("token", "idempotent:token:ed189572-55b5-428f-b4ef-b6299e347518");

		return new HttpEntity(headers);
	}
}
