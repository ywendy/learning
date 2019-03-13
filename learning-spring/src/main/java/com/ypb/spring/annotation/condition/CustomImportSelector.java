package com.ypb.spring.annotation.condition;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @ClassName: CustomImportSelector
 * @Description: 自定义逻辑返回需要导入的组件
 * @author yangpengbing
 * @date 2019/3/12-17:50
 * @version V1.0.0
 *
 */
public class CustomImportSelector implements ImportSelector {

	/**
	 * 返回值就是要导入到容器中组件的全类名 方法不能返回null
	 * @param importingClassMetadata 当前标注@Import注解的类的所有注解信息
	 * @return
	 */
	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {

		return new String[]{"com.ypb.spring.annotation.bean.Blue", "com.ypb.spring.annotation.bean.Yellow"};
	}

}
