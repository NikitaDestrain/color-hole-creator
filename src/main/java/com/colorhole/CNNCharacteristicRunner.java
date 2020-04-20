package com.colorhole;

import com.colorhole.utils.FileUtils;
import com.colorhole.utils.ImageRW;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class CNNCharacteristicRunner {

    // constants
    private static final int BLACK = -16777216;
    private static final int WHITE = -1;

    // params per run
    private static final String DATASET_PATH = "cnn-results/DFNet_rectangle/";
    private static final String MASKS_PATH = "inv_masks";
    private static final String DETECTED_MASKS_PATH = "5_10_detected_035";

    private static final String MASKS_FLIST_NAME = "flist";
    private static final String ETECTED_MASKS_FLIST_NAME = "flist";

    public static void main(String[] args) {
        ImageRW imageRW = ImageRW.getInstance();
        FileUtils fu = FileUtils.getInstance();

        List<String> maskImageNames = fu.getNameListForPath(DATASET_PATH + MASKS_PATH + "/", MASKS_FLIST_NAME);
        List<String> detectedMaskImageNames = fu.getNameListForPath(DATASET_PATH + DETECTED_MASKS_PATH + "/", ETECTED_MASKS_FLIST_NAME);

        int maskSize = maskImageNames.size();
        int detectedMaskSize = detectedMaskImageNames.size();

        if (maskSize != detectedMaskSize) {
            System.out.println("ERROR: mask size should equal detected mask size");
            System.exit(1);
        }

        double maxOverallSuccessRate = Double.MIN_VALUE;
        double maxOverallLoseRate = Double.MIN_VALUE;

        double sumOfSuccessRate = 0.0;
        double sumOfLoseRate = 0.0;

        for (int ind = 0; ind < maskSize; ind++) {
            String maskName = maskImageNames.get(ind);
            String detectedMaskName = detectedMaskImageNames.get(ind);

            System.out.println(maskName + " ~ " + detectedMaskName);
            BufferedImage mask = imageRW.readImageByFullPath(DATASET_PATH + MASKS_PATH + "/" + maskName, true);
            // transform to black and white
            BufferedImage bwMask = new BufferedImage(
                    mask.getWidth(), mask.getHeight(),
                    BufferedImage.TYPE_BYTE_BINARY);

            Graphics2D graphics = bwMask.createGraphics();
            graphics.drawImage(mask, 0, 0, null);

            BufferedImage detectedMask = imageRW.readImageByFullPath(DATASET_PATH + DETECTED_MASKS_PATH + "/" + detectedMaskName, true);

            int maskW = mask.getWidth();
            int maskH = mask.getHeight();

            int detectedMaskW = detectedMask.getWidth();
            int detectedMaskH = detectedMask.getHeight();

            if (maskW != detectedMaskW || maskH != detectedMaskH) {
                System.out.println("ERROR: mask width and height should equal detected mask");
                System.exit(1);
            }

            int failCnt = 0;
            int loseCnt = 0;

            int pixelMaskCnt = 0;
            int pixelDetectedMaskCnt = 0;

            for (int w = 0; w < maskW; w++) {
                for (int h = 0; h < maskH; h++) {
                    int maskColor = bwMask.getRGB(w, h);
                    int detectedMaskColor = detectedMask.getRGB(w, h);

                    if (maskColor == WHITE) {
                        pixelMaskCnt++;
                    }
                    if (detectedMaskColor == WHITE) {
                        pixelDetectedMaskCnt++;
                    }

                    if (maskColor != detectedMaskColor) {
                        if (maskColor == WHITE) {
                            loseCnt++;
                        }
                        if (maskColor == BLACK) {
                            failCnt++;
                        }
                    }
                }
            }

/*
            System.out.println("    Black lose: " + failCnt);
            System.out.println("    White lose: " + loseCnt);
            System.out.println("    White count: " + pixelMaskCnt);
            System.out.println("    White count for detected: " + pixelDetectedMaskCnt);
*/

            // calculate
            double failRate = failCnt == 0 ? 0.0 : (double) failCnt / (double) pixelDetectedMaskCnt;
            double successRate = failCnt == 0 ? 0.0 : (double) (pixelDetectedMaskCnt - failCnt) / (double) pixelDetectedMaskCnt;

            double overallSuccessRate = (double) (pixelMaskCnt - loseCnt) / (double) pixelMaskCnt;
            double overallLoseRate = (double) (loseCnt) / (double) pixelMaskCnt;

            sumOfSuccessRate += overallSuccessRate;
            sumOfLoseRate += overallLoseRate;

            if (overallLoseRate > maxOverallLoseRate) {
                maxOverallLoseRate = overallLoseRate;
            }

            if (overallSuccessRate > maxOverallSuccessRate) {
                maxOverallSuccessRate = overallSuccessRate;
            }

            System.out.println("    failRate: " + failRate);
            System.out.println("    successRate: " + successRate);
            System.out.println("    overallSuccessRate: " + overallSuccessRate);
            System.out.println("    overallLoseRate: " + overallLoseRate);
        }

        System.out.println();
        System.out.println("Max overall success rate: " + maxOverallSuccessRate);
        System.out.println("Max overall lose rate: " + maxOverallLoseRate);

        System.out.println("Mean overall success rate: " + sumOfSuccessRate / maskSize);
        System.out.println("Mean overall lose rate: " + sumOfLoseRate / maskSize);
    }
}
