package com.colorhole;

import com.colorhole.statistic.StatisticalCharacteristic;

import java.awt.image.BufferedImage;
import java.util.List;

public class StatisticalCharacteristicRunner {
    public static void main(String[] args) {
        ImageRW imageRW = ImageRW.getInstance();
        List<String> list = imageRW.getNameListForPath(Constants.CONTEXT_ENCODER_IMAGE_PATH, "helper");
        for (String name : list) {
            StatisticalCharacteristic statisticalCharacteristic = StatisticalCharacteristic.getInstance();

            BufferedImage image1 = imageRW.readImageByFullPath(Constants.CONTEXT_ENCODER_IMAGE_PATH + name + "_gth.png");
            BufferedImage image2 = imageRW.readImageByFullPath(Constants.CONTEXT_ENCODER_IMAGE_PATH + name + "_out.png");

            double similarImageMSE = statisticalCharacteristic.calculateMSEForSimilarSizeImages(image1, image1);
            double MSE = statisticalCharacteristic.calculateMSEForSimilarSizeImages(image1, image2);
            System.out.println("[TEST]: Similar image MSE = " + similarImageMSE);
            System.out.println("[RESULT]: MSE = " + MSE);
        }
    }
}
