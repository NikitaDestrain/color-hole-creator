package com.colorhole;

import com.colorhole.statistic.StatisticalCharacteristic;

import java.awt.image.BufferedImage;

public class StatisticalCharacteristicRunner {
    public static void main(String[] args) {
        ImageRW imageRW = ImageRW.getInstance();
        StatisticalCharacteristic statisticalCharacteristic = StatisticalCharacteristic.getInstance();

        BufferedImage image1 = imageRW.readImageByFullPath(Constants.SOURCE_IMAGE_PATH + "example.jpg");
        BufferedImage image2 = imageRW.readImageByFullPath(Constants.TARGET_IMAGE_PATH + "black-hole-example.jpg");

        double similarImageMSE = statisticalCharacteristic.calculateMSEForSimilarSizeImages(image1, image1);
        double MSE = statisticalCharacteristic.calculateMSEForSimilarSizeImages(image1, image2);
        System.out.println("[TEST]: Similar image MSE = "+ similarImageMSE);
        System.out.println("[RESULT]: MSE = " + MSE);
    }
}
