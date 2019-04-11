package com.colorhole;

import com.colorhole.other.OtherConstants;
import com.colorhole.statistic.StatisticalCharacteristic;
import com.colorhole.utils.FileUtils;
import com.colorhole.utils.ImageRW;

import java.awt.image.BufferedImage;
import java.util.List;

public class StatisticalCharacteristicRunner {
    public static void main(String[] args) {
        ImageRW imageRW = ImageRW.getInstance();
        FileUtils fu = FileUtils.getInstance();
        List<String> list = fu.getNameListForPath(OtherConstants.CONTEXT_ENCODER_IMAGE_PATH, "helper");

        for (String name : list) {
            StatisticalCharacteristic statisticalCharacteristic = StatisticalCharacteristic.getInstance();

            BufferedImage image1 = imageRW.readImageByFullPath(OtherConstants.CONTEXT_ENCODER_IMAGE_PATH + name + "_gth.png", true);
            BufferedImage image2 = imageRW.readImageByFullPath(OtherConstants.CONTEXT_ENCODER_IMAGE_PATH + name + "_out.png", true);

            // uncomment for debug
            //double similarImageMSE = statisticalCharacteristic.calculateMSEForSimilarSizeImages(image1, image1);
            double MSE = statisticalCharacteristic.calculateMSEForSimilarSizeImages(image1, image2);
            // System.out.println("[TEST]: Similar image MSE = " + similarImageMSE);
            System.out.println("[RESULT]: MSE = " + MSE);
        }
    }
}
