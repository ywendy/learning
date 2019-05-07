package com.ypb.redis.lock;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.config.Config;

/**
 * @ClassName: RedlockTest
 * @Description: Redis分布式锁最牛逼的实现(https://www.jianshu.com/p/7e47a4503b87)
 * @author yangpengbing
 * @date 2019/3/11-15:35
 * @version V1.0.0
 *
 */
@Slf4j
public class RedlockTest {

	private static RedissonRedLock redLock = null;

	static {
		String key = "REDLOCK_KEY";

		List<Config> configs = Lists.newArrayList();
		configs.add(initConfig("127.0.0.1:6374", null, 0));
		configs.add(initConfig("127.0.0.1:6375", null, 1));
		configs.add(initConfig("127.0.0.1:6376", null, 2));
		configs.add(initConfig("127.0.0.1:6377", null, 3));
		configs.add(initConfig("127.0.0.1:6378", null, 5));

		redLock = initRedissonRedLock(configs, key);
	}

	public static void main(String[] args) {
		int times = 10;

		try {
			for (int i = 0; i < times; i++) {
				int finalI = i;
				new Thread(() -> {
					boolean lock = getLock();

					String str = String.format("loop {%d}, lock {%s}", finalI, lock);

					System.out.println(str);
				}).start();

				TimeUnit.MILLISECONDS.sleep(1000L);
			}
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}
	}

	private static boolean getLock() {
		boolean lock = false;

		try {
			lock = redLock.tryLock(500, 10000, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		} finally {
			redLock.unlock();
		}

		return lock;
	}

	private static RedissonRedLock initRedissonRedLock(List<Config> configs, String key) {
		RLock[] locks = configs.stream().map(config -> Redisson.create(config).getFairLock(key)).toArray(RLock[]::new);

		return new RedissonRedLock(locks);
	}

	/**
	 * 创建config对象
	 * @param address
	 * @param password
	 * @param database
	 * @return
	 */
	private static Config initConfig(String address, String password, int database) {
		Config config = new Config();
		config.useSingleServer().setAddress(address).setFailedAttempts(1).setPassword(password).setConnectTimeout(1000).setPingTimeout(500).setTimeout(3000).setDatabase(database);

		return config;
	}
}
