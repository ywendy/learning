package com.ypb.concurrent.chapter2;

import lombok.Data;

/**
 * @className TaxCalculator
 * @description 税率计算器
 * @author yangpengbing
 * @date 0:25 2019/4/5
 * @version 1.0.0
 */
@Data
public class TaxCalculator {

    /**
     * 薪水
     */
    private final double salary;

    private final double bonus;

    private CalculatorStrategy calculatorStrategy;

    public TaxCalculator(double salary, double bonus, CalculatorStrategy calculatorStrategy) {
        this.salary = salary;
        this.bonus = bonus;
        this.calculatorStrategy = calculatorStrategy;
    }

    protected double calcTax() {
        return calculatorStrategy.calculate(salary, bonus);
    }

    public double calculate() {
        return calcTax();
    }
}
