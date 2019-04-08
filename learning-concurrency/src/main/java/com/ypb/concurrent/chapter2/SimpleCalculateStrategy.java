package com.ypb.concurrent.chapter2;

public class SimpleCalculateStrategy implements CalculatorStrategy {

    protected static final double SALARTY_RATIO = 0.1;
    protected static final double BONUS_RATIO = 0.15;

    @Override
    public double calculate(double salary, double bonus) {
        return salary * SALARTY_RATIO + bonus * BONUS_RATIO;
    }
}
