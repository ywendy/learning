package com.ypb.concurrent.stage1.chapter2;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yangpengbing
 * @version 1.0.0
 * @className TaxCalculatorMain
 * @description 税率计算器Main类
 * @date 1:18 2019/4/5
 */
@Slf4j
public class TaxCalculatorMain {

    public static void main(String[] args) {
        // 这样不好，因为如果计算税率和公式发生了变化，需要修改源代码的逻辑，容易造成系统的不稳定
//        TaxCalculator tc = new TaxCalculator(10000d, 2000d) {
//
//            @Override
//            protected double calcTax() {
//                return getSalary() * 0.1 + getBonus() * 0.15;
//            }
//        };
//
//        double calc = tc.calculate();
//        log.info("calcTax {}", calc);

        double salary = 10000d;
        double bonus = 2000d;

        TaxCalculator tc = new TaxCalculator(salary, bonus, (s, b) -> s * SimpleCalculateStrategy.SALARTY_RATIO + b * SimpleCalculateStrategy.BONUS_RATIO);
        CalculatorStrategy cs = new SimpleCalculateStrategy();
        tc.setCalculatorStrategy(cs);

        double calc = tc.calculate();
        log.info("clac {}", calc);
    }
}
