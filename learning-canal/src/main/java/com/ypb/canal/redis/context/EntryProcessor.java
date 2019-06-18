package com.ypb.canal.redis.context;

import com.google.common.collect.Maps;
import com.ypb.canal.redis.entry.CustomDbObj;
import com.ypb.canal.redis.utils.ClassScanner;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

//@Component
public class EntryProcessor implements BeanFactoryPostProcessor {

	private static final String ENTRY_PACKAGE = "com.ypb.canal.redis.entry";

	private static CustomDbObj apply(Class<?> clazz) {
		if (clazz == CustomDbObj.class) {
			return null;
		}

		try {
			return (CustomDbObj) clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		Map<String, Class> entryMap = Maps.newHashMap();
		Set<Class<?>> classes = ClassScanner.scanAllClass(ENTRY_PACKAGE);

		if (CollectionUtils.isEmpty(classes)) {
			return;
		}

		List<CustomDbObj> objs = classes.stream().map(EntryProcessor::apply).filter(Objects::nonNull).collect(Collectors.toList());

		if (CollectionUtils.isEmpty(objs)) {
			return;
		}

		objs.forEach(obj -> {
			entryMap.put(obj.getKey(), obj.getClass());
			beanFactory.registerSingleton(obj.getClass().getName(), obj);
		});

		EntryContext context = new EntryContext(entryMap);
		beanFactory.registerSingleton(context.getClass().getName(), context);
	}
}
