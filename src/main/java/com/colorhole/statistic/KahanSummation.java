package com.colorhole.statistic;

import java.math.BigDecimal;

public class KahanSummation {


    private double sum = 0;
    private BigDecimal deciSum = new BigDecimal("0.0");

    private double c = 0;

    private double y, t;

    public synchronized void add(double in) {
        y = in - c;
        t = sum + y;
        c = (t - sum) - y;
        sum = t;
        deciSum = deciSum.add(new BigDecimal(in));
    }

    public double getSum() {

        BigDecimal diff = deciSum.subtract(new BigDecimal(sum));
        if (diff.doubleValue() != 0.0) {
            System.out.println("Kahan summation disagrees with BigDecimal by " + diff);
        }
        return sum;
    }
}