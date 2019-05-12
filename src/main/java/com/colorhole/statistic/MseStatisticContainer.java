package com.colorhole.statistic;

import lombok.Data;

@Data
public class MseStatisticContainer {
    private String originalImageName;
    private int imageHeight;
    private int imageWidth;
    private int amount;
    private double mse;
}
