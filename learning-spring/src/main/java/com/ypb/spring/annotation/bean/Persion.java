package com.ypb.spring.annotation.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

/**
 * @className Persion
 * @description 定义Persion实体类
 * @author yangpengbing
 * @date 23:49 2019/3/9
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Persion {

	@Value("${persion.name}")
	@NonNull
    private String name;
	@Value("#{32 - 1}")
	@NonNull
    private int age;
	@Value("happy518")
	private String nickName;
}
