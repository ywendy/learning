package com.ypb.spring.annotation.config;

import java.io.IOException;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

public class CustomTypeFilter implements TypeFilter {

	/**
	 * @param metadataReader 读取到的当前正在扫描的类的信息
	 * @param metadataReaderFactory 可以获取到其他任何类信息的
	 * @return
	 * @throws IOException
	 */
	@Override
	public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
			throws IOException {
		// 获取当前类的注解信息
		AnnotationMetadata metadata = metadataReader.getAnnotationMetadata();

		// 获取当前正在扫描的类的类信息
		ClassMetadata classMetadata = metadataReader.getClassMetadata();

		// 获取当前类资源(类路径)
		Resource resource = metadataReader.getResource();

		String className = classMetadata.getClassName();
		System.out.println("className = " + className);

		if (className.contains("ao")) {
			return true;
		}

		return false;
	}
}
