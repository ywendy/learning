package com.ypb.spring.annotation.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class Persion {

    private String name;
    private String age;
}
