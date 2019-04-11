package com.colorhole.statistic;

import java.awt.image.BufferedImage;

public class StatisticalCharacteristic {

    private static StatisticalCharacteristic instance;

    private StatisticalCharacteristic() {
    }

    public static StatisticalCharacteristic getInstance() {
        if (instance == null) instance = new StatisticalCharacteristic();
        return instance;
    }

    public double calculateMSEForSimilarSizeImages(BufferedImage image1, BufferedImage image2) {
        int width = image1.getWidth();
        int height = image1.getHeight();

        double MSE = 0;
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                int argb1 = image1.getRGB(column, row);
                int argb2 = image2.getRGB(column, row);
                int argbDiff = argb1 - argb2;
                MSE += (double) argbDiff * argbDiff;
            }
        }

        MSE /= width * height;
        return MSE;
    }
}
