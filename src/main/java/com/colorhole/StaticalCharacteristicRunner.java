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
        String postFix = runProperties.getProperty(PropertyConstants.INPAINT_POSTFIX_PROPERTY);
        int amount = Integer.parseInt(runProperties.getProperty(PropertyConstants.AMOUNT_PROPERTY));

        String datasetPath = RunnerConstants.IMG_PATH_FILE_NAME + subPath + "/";
        PropertyParser descriptorProperties = new PropertyParser(datasetPath, RunnerConstants.DESCRIPTOR_FILE_NAME);
        String extension = descriptorProperties.getProperty(PropertyConstants.EXTENSION_PROPERTY);
        String inputFlistFileName = descriptorProperties.getProperty(PropertyConstants.INPUT_FLIST_PROPERTY);
        String inpaintFlistFileName = descriptorProperties.getProperty(PropertyConstants.INPAINT_FLIST_PROPERTY);
        String mseStatisticFileName = descriptorProperties.getProperty(PropertyConstants.MSE_STATISTIC_PROPERTY);
        String psnrStatisticFileName = descriptorProperties.getProperty(PropertyConstants.PSNR_STATISTIC_PROPERTY);
        String statisticCharacteristicFileName = descriptorProperties.getProperty(PropertyConstants.STATISTICAL_CHARACTERISTIC_PROPERTY);
        //String statisticOutPutFileName = descriptorProperties.getProperty(PropertyConstants.STATISTIC_PROPERTY);

        List<String> inputImageNames = fu.getNameListForPath(datasetPath + subInputPath + "/", inputFlistFileName);
        List<String> inpaintImageNames = fu.getNameListForPath(datasetPath + subInpaintPath + "/", inpaintFlistFileName);
        List<MseStatisticContainer> mseStatisticContainers = new ArrayList<>();
        List<Double> mseList = new ArrayList<>();
        List<Double> psnrList = new ArrayList<>();

        double mean = 0.0;
        double psnrMean = 0.0;
        for (String inputFileName : inputImageNames) {

            String originalNameWithExt = inputFileName + "." + extension;
            String inpaintNameWithExt = "result-" + inputFileName + "-" + inputFileName + "_hole_mask_0" + "." + "png"; // fixme change to name from flist

            String originalFullPath = datasetPath + subInputPath + "/" + originalNameWithExt;
            String inpaintFullPath = datasetPath + subInpaintPath + "/" + inpaintNameWithExt;

            BufferedImage original = imageRW.readImageByFullPath(originalFullPath, false);
            BufferedImage inpaint = imageRW.readImageByFullPath(inpaintFullPath, false);

            double mse = sc.calculateMSEForSimilarSizeImages(original, inpaint);
            double psnr = sc.psnr(original, inpaint);
            int imageHeight = original.getHeight();
            int imageWidth = original.getWidth();

            // logs
            System.out.println("[INFO]: Original image - " + originalNameWithExt);
            System.out.println("[INFO]: Inpaint image - " + inpaintNameWithExt);
            System.out.println("[RESULT]: MSE = " + mse);
            System.out.println("[RESULT]: PSNR = " + psnr);
            System.out.println();

            MseStatisticContainer mseStatisticContainer = new MseStatisticContainer();
            mseStatisticContainer.setAmount(amount);
            mseStatisticContainer.setMse(mse);
            mseStatisticContainer.setImageHeight(imageHeight);
            mseStatisticContainer.setImageWidth(imageWidth);
            mseStatisticContainer.setOriginalImageName(inputFileName);
            mseStatisticContainer.setPsnr(psnr);
            mseStatisticContainers.add(mseStatisticContainer);

            mean += mse;
            psnrMean += psnr;

            mseList.add(mse);
            psnrList.add(psnr);
        }
        // calculate unbiased sample variance and mean
        mean /= mseList.size();
        double variance = 0.0;
        for (double mse : mseList) {
            variance += ((mse * mse) - (mean * mean));
        }
        variance /= (mseList.size() - 1);

        psnrMean /= psnrList.size();
        double psnrVariance = 0.0;
        for (double psnr : psnrList) {
            psnrVariance += ((psnr * psnr) - (psnrMean * psnrMean));
        }
        psnrVariance /= (psnrList.size() - 1);

        System.out.println("[RESULT]: MSE mean = " + mean);
        System.out.println("[RESULT]: MSE unbiased sample variance = " + variance);
        System.out.println();
        System.out.println("[RESULT]: PSNR mean = " + psnrMean);
        System.out.println("[RESULT]: PSNR unbiased sample variance = " + psnrVariance);
        System.out.println();
        fu.writeMSEStatisticByFullPath(datasetPath + mseStatisticFileName, mseStatisticContainers);
        fu.writePSNRStatisticByFullPath(datasetPath + psnrStatisticFileName, mseStatisticContainers);
        fu.writeStatisticByFullPath(datasetPath + statisticCharacteristicFileName, mean, variance, psnrMean, psnrVariance);
    }
}