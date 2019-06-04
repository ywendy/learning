package com.ypb.canal.redis.utils;

import com.ypb.canal.redis.annotation.PrimaryKey;
import java.lang.reflect.Field;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.CollectionUtils;

/**
 * @ClassName: PrimaryKeyHandler
 * @Description: 获取注解值
 * @author 杨鹏兵
 * @date 2016年8月4日-下午2:24:43
 * @version V1.0.0
 *
 */
public class PrimaryKeyHandler {
	
	public static Object getPrimaryKeyValue(Class<?> clazz,Object obj){
		Field[] fields = clazz.getDeclaredFields();

		Object keyObj = null;
		if (ArrayUtils.isEmpty(fields)) {
			return keyObj;
		}

		for (Field field : fields) {
			field.setAccessible(true);
			if(field.isAnnotationPresent(PrimaryKey.class)){
				try {
					keyObj = field.get(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		}

		return keyObj;
	}

}
