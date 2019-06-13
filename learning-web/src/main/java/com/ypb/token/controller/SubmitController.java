package com.ypb.token.controller;

import com.ypb.token.annoation.NoRepeatSubmit;
import com.ypb.token.entry.ApiResult;
import com.ypb.token.entry.UserEntry;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: SubmitController
 * @Description: 模拟提交的controller
 * @author yangpengbing
 * @date 2019-06-12-10:26
 * @version V1.0.0
 *
 */
@Slf4j
@RestController
public class SubmitController {

	private static final Random random = new Random(System.currentTimeMillis());

	@PostMapping("/submit")
	@NoRepeatSubmit(lockTime = 30)
	public ApiResult submit(UserEntry entry) {
		try {
			slowly();
		} catch (InterruptedException e) {
			log.debug(e.getMessage(), e);
		}

		ApiResult result = new ApiResult(HttpStatus.OK.value(), "成功");
		result.setData(entry.getUserId());
		return result;
	}

	private void slowly() throws InterruptedException {
		int bound = 59;
		bound = random.nextInt(bound) + 1;

		log.info("sleep {}", bound);
		TimeUnit.SECONDS.sleep(random.nextInt(bound));
	}
}
