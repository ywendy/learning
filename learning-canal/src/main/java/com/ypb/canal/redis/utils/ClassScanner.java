package com.ypb.canal.redis.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.SystemPropertyUtils;

@Slf4j
public final class ClassScanner implements ResourceLoaderAware {

	private final List<TypeFilter> includeFilters = Lists.newLinkedList();
	private final List<TypeFilter> excludeFilters = Lists.newLinkedList();

	private ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
	private MetadataReaderFactory factory = new CachingMetadataReaderFactory(this.resolver);

	@SafeVarargs
	public static Set<Class<?>> scan(String basePackages, Class<? extends Annotation>... annotions) {
		return scan(StringUtils.tokenizeToStringArray(basePackages, ",; \t\n"), annotions);
	}

	@SafeVarargs
	public static Set<Class<?>> scan(String[] basePackages, Class<? extends Annotation>... annotions) {
		ClassScanner cs = new ClassScanner();

		if (ArrayUtils.isNotEmpty(annotions)) {
			Arrays.stream(annotions).forEach(cs::addIncludeFilters);
		}

		Set<Class<?>> classes = Sets.newHashSet();
		Arrays.stream(basePackages).map(cs::doScan).forEach(classes::addAll);

		return classes;
	}

	private Set<Class<?>> doScan(String basePackage) {
		Set<Class<?>> classes = Sets.newHashSet();

		try {
			String path = convertClassNameToResourcePath(basePackage);

			Resource[] resources = this.resolver.getResources(path);

			Arrays.stream(resources).forEach(resource -> classes.addAll(filters(resource)));
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}

		return classes;
	}

	private Set<Class<?>> filters(Resource resource) {
		if (!resource.isReadable()) {
			return Sets.newHashSet();
		}

		Set<Class<?>> classes = Sets.newHashSet();
		try {
			MetadataReader reader = factory.getMetadataReader(resource);

			boolean flag = (CollectionUtils.isEmpty(includeFilters) && CollectionUtils.isEmpty(excludeFilters)
					|| matches(reader));
			if (flag) {
				classes.add(Class.forName(reader.getClassMetadata().getClassName()));
			}
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}

		return classes;
	}

	private boolean matches(MetadataReader reader) throws IOException {
		for (TypeFilter filter : excludeFilters) {
			if (filter.match(reader, factory)) {
				return false;
			}
		}

		for (TypeFilter filter : includeFilters) {
			if (filter.match(reader, factory)) {
				return true;
			}
		}

		return false;
	}

	private String convertClassNameToResourcePath(String basePackage) {
		return ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils
				.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage)) + "/**/*.class";
	}

	private void addIncludeFilters(Class<? extends Annotation> annotation) {
		AnnotationTypeFilter filter = new AnnotationTypeFilter(annotation);

		includeFilters.add(filter);
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
		this.factory = new CachingMetadataReaderFactory(resourceLoader);
	}
}
