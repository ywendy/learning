package com.ypb.canal.redis.utils;

/**
 * @ClassName: SpringContextAware
 * @Description: Spring 上下文初始化/销毁
 * @author 杨鹏兵
 * @date 2016年9月8日-下午5:07:29
 * @version V1.0.0
 *
 */
public interface SpringContextAware {
	/**
	 * @Title: initialize
	 * @Description: Spring 上下文初始化
	 * @author 杨鹏兵
	 * @date 2016年9月8日-下午5:09:21
	 *
	 */
	void initialize();
	
	/**
	 * @Title: destroy
	 * @Description: Spring 上下文销毁
	 * @author 杨鹏兵
	 * @date 2016年9月8日-下午5:09:39
	 *
	 */
	void destroy();
}
