package com.ypb.concurrent.stage1.chapter2;
/**
 * @className CalculatorStrategy
 * @description 计算税率的策略(公式)
 * @author yangpengbing
 * @date 1:25 2019/4/5
 * @version 1.0.0
 */
@FunctionalInterface
public interface CalculatorStrategy {

    double calculate(double salary, double bonus);
}
