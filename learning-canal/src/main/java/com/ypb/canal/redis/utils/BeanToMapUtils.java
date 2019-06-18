package com.ypb.canal.redis.utils;

import com.google.common.collect.Maps;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

/**
 * @ClassName: BeanToMapUtils
 * @Description: TODO
 * @author 杨鹏兵
 * @date 2016年11月22日-下午4:21:54
 * @version V1.0.0
 *
 */
public class BeanToMapUtils {

	/**
	 * @Title: convertMap
	 * @Description: map转对象
	 * @author 杨鹏兵
	 * @param <T>
	 * @date 2016年11月22日-下午4:22:55
	 * @param type
	 * @param map
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 *
	 */
	public static <T> T convertMap(Class<T> type, Map map) throws Exception{
		T t = type.newInstance();
		ConvertUtils.register(new DateConverter(), Date.class);
		
		BeanUtils.populate(t, map);
		return t;
	}
	
	/**
	 * @Title: convertBean
	 * @Description: bean转map
	 * @author 杨鹏兵
	 * @date 2016年11月22日-下午6:11:06
	 * @param bean
	 * @param isIgnoreNull 默认将null转""
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 *
	 */
	public static Map<String, Object> convertBean(Object bean, boolean isIgnoreNull) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
	    Class type = bean.getClass();
	    Map<String, Object> returnMap = Maps.newHashMap();
	    BeanInfo beanInfo = Introspector.getBeanInfo(type);

	    PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

	    for (int i = 0; i < propertyDescriptors.length; ++i) {
	      PropertyDescriptor descriptor = propertyDescriptors[i];
	      String propertyName = descriptor.getName();
	      if (!(propertyName.equals("class"))) {
	        Method readMethod = descriptor.getReadMethod();
	        Object result = readMethod.invoke(bean, new Object[0]);
		      if (result != null) {
			      if (result instanceof Date) {
				      Date d = (Date) result;
				      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				      returnMap.put(propertyName, format.format(d));
			      } else {
				      returnMap.put(propertyName, result);
			      }
		      } else {
			      if (!isIgnoreNull) {
				      returnMap.put(propertyName, null);
			      }
		      }
	      }
	    }
	    return returnMap;
	}
	
	public static Map<String, Object> convertBean(Object bean) throws IllegalAccessException, InvocationTargetException, IntrospectionException{
		return convertBean(bean, false);
	}
}
