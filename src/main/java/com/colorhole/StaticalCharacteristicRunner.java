package com.colorhole;

import com.colorhole.statistic.MseStatisticContainer;
import com.colorhole.statistic.StatisticalCharacteristic;
import com.colorhole.utils.FileUtils;
import com.colorhole.utils.ImageRW;
import com.colorhole.utils.PropertyConstants;
import com.colorhole.utils.PropertyParser;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class StaticalCharacteristicRunner {
    public static void main(String[] args) throws Exception {
        PropertyParser runProperties = new PropertyParser(PropertyConstants.PATH_TO_RUN_PROPERTY, "configuration.properties");
        StatisticalCharacteristic sc = StatisticalCharacteristic.getInstance();
        FileUtils fu = FileUtils.getInstance();
        ImageRW imageRW = ImageRW.getInstance();

        String subPath = runProperties.getProperty(PropertyConstants.PATH_PROPERTY);
        String subInputPath = runProperties.getProperty(PropertyConstants.INPUT_SUB_PATH_PROPERTY);
        //String subOutputPath = runProperties.getProperty(PropertyConstants.OUTPUT_SUB_PATH_PROPERTY);
        String subInpaintPath = runProperties.getProperty(PropertyConstants.INPAINT_SUB_PATH_PROPERTY);
        String postFix = runProperties.getProperty(PropertyConstants.OUTPUT_POSTFIX_PROPERTY);
        int amount = Integer.parseInt(runProperties.getProperty(PropertyConstants.AMOUNT_PROPERTY));

        String datasetPath = RunnerConstants.IMG_PATH_FILE_NAME + subPath + "/";
        PropertyParser descriptorProperties = new PropertyParser(datasetPath, RunnerConstants.DESCRIPTOR_FILE_NAME);
        String extension = descriptorProperties.getProperty(PropertyConstants.EXTENSION_PROPERTY);
        //String inputFlistFileName = descriptorProperties.getProperty(PropertyConstants.INPUT_FLIST_PROPERTY);
        String inpaintFlistFileName = descriptorProperties.getProperty(PropertyConstants.INPAINT_FLIST_PROPERTY);
        String mseStatisticFileName = descriptorProperties.getProperty(PropertyConstants.MSE_STATISTIC_PROPERTY);
        //String statisticOutPutFileName = descriptorProperties.getProperty(PropertyConstants.STATISTIC_PROPERTY);

        //List<String> inputImageNames = fu.getNameListForPath(datasetPath + subInputPath + "/", inputFlistFileName);
        List<String> inpaintImageNames = fu.getNameListForPath(datasetPath + subInpaintPath + "/", inpaintFlistFileName);
        List<MseStatisticContainer> mseStatisticContainers = new ArrayList<>();

        for (String inpaintFileName : inpaintImageNames) {
            // get original name
            String originalName = inpaintFileName.split(("_" + postFix))[0];

            String originalFullPath = datasetPath + subInputPath + "/" + originalName + "." + extension;
            String inpaintFullPath = datasetPath + subInpaintPath + "/" + inpaintFileName + "." + extension;

            BufferedImage original = imageRW.readImageByFullPath(originalFullPath, false);
            BufferedImage inpaint = imageRW.readImageByFullPath(inpaintFullPath, false);

            double mse = sc.calculateMSEForSimilarSizeImages(original, inpaint);
            int imageHeight = original.getHeight();
            int imageWidth = original.getWidth();

            // logs
            System.out.println("Original image: " + originalName);
            System.out.println("Inpaint image: " + inpaintFileName);
            System.out.println("MSE: " + mse);
            System.out.println();

            MseStatisticContainer mseStatisticContainer = new MseStatisticContainer();
            mseStatisticContainer.setAmount(amount);
            mseStatisticContainer.setMse(mse);
            mseStatisticContainer.setImageHeight(imageHeight);
            mseStatisticContainer.setImageWidth(imageWidth);
            mseStatisticContainer.setOriginalImageName(originalName);
            mseStatisticContainers.add(mseStatisticContainer);
        }
        fu.writeMSEStatisticByFullPath(datasetPath + mseStatisticFileName, mseStatisticContainers);
    }
}