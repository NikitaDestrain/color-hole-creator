package com.colorhole;

import com.colorhole.hole.ColorConstants;
import com.colorhole.hole.HoleCreator;
import com.colorhole.hole.HoleCreatorUtils;
import com.colorhole.statistic.StatisticContainer;
import com.colorhole.utils.FileUtils;
import com.colorhole.utils.ImageRW;
import com.colorhole.utils.PropertyConstants;
import com.colorhole.utils.PropertyParser;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Runner {

    public static void main(String[] args) throws Exception {
        PropertyParser runProperties = new PropertyParser(PropertyConstants.PATH_TO_RUN_PROPERTY, "configuration.properties");
        FileUtils fu = FileUtils.getInstance();
        ImageRW iRW = ImageRW.getInstance();
        HoleCreator hC = HoleCreator.getInstance();
        HoleCreatorUtils hCU = HoleCreatorUtils.getInstance();

        // process config file
        String subPath = runProperties.getProperty(PropertyConstants.PATH_PROPERTY);
        String subInputPath = runProperties.getProperty(PropertyConstants.INPUT_SUB_PATH_PROPERTY);
        String subOutputPath = runProperties.getProperty(PropertyConstants.OUTPUT_SUB_PATH_PROPERTY);
        String subMasksPath = runProperties.getProperty(PropertyConstants.MASKS_SUB_PATH_PROPERTY);
        int amount = Integer.parseInt(runProperties.getProperty(PropertyConstants.AMOUNT_PROPERTY));
        String outPostfix = runProperties.getProperty(PropertyConstants.OUTPUT_POSTFIX_PROPERTY);
        String form = runProperties.getProperty(PropertyConstants.FORM_PROPERTY);
        int minHeight = Integer.parseInt(runProperties.getProperty(PropertyConstants.MIN_HEIGHT_PROPERTY));
        int maxHeight = Integer.parseInt(runProperties.getProperty(PropertyConstants.MAX_HEIGHT_PROPERTY));
        int minWidth = Integer.parseInt(runProperties.getProperty(PropertyConstants.MIN_WIDTH_PROPERTY));
        int maxWidth = Integer.parseInt(runProperties.getProperty(PropertyConstants.MAX_WIDTH_PROPERTY));
        String color = runProperties.getProperty(PropertyConstants.COLOR_PROPERTY);

        // process color
        int argbColor = ColorConstants.BLACK_COLOR_ARGB;
        switch (color) {
            case "red":
                argbColor = ColorConstants.RED_COLOR_ARGB;
                break;
            case "green":
                argbColor = ColorConstants.GREEN_COLOR_ARGB;
                break;
            case "blue":
                argbColor = ColorConstants.BLUE_COLOR_ARGB;
                break;
            case "white":
                argbColor = ColorConstants.WHITE_COLOR_ARGB;
                break;
            case "black":
                argbColor = ColorConstants.BLACK_COLOR_ARGB;
                break;
            default:
                System.out.println("[WARNING]: Incorrect value of color parameter: " + color + " - the color does not support");
                System.out.println("[INFO]: Use black color by default");
                break;
        }

        // process form
        HoleCreator.HoleForm holeForm;
        switch (form) {
            case "rectangle":
                holeForm = HoleCreator.HoleForm.RECTANGLE;
                break;
            case "ellipse":
                holeForm = HoleCreator.HoleForm.ELLIPSE;
                break;
            default:
                holeForm = HoleCreator.HoleForm.RECTANGLE;
                break;
        }

        // process descriptor.properties
        String datasetPath = RunnerConstants.IMG_PATH_FILE_NAME + subPath + "/";
        PropertyParser descriptorProperties = new PropertyParser(datasetPath, RunnerConstants.DESCRIPTOR_FILE_NAME);
        String extension = descriptorProperties.getProperty(PropertyConstants.EXTENSION_PROPERTY);
        String inputFlistFileName = descriptorProperties.getProperty(PropertyConstants.INPUT_FLIST_PROPERTY);
        String outputFlistFileName = descriptorProperties.getProperty(PropertyConstants.OUTPUT_FLIST_PROPERTY);
        String masksFlistFileName = descriptorProperties.getProperty(PropertyConstants.MASKS_FLIST_PROPERTY);
        String statisticOutPutFileName = descriptorProperties.getProperty(PropertyConstants.STATISTIC_PROPERTY);

        // process file names file
        List<String> inputImageNames = fu.getNameListForPath(datasetPath + subInputPath + "/", inputFlistFileName);
        List<String> outputFileNames = new ArrayList<>();
        List<String> maskFileNames = new ArrayList<>();
        List<StatisticContainer> statisticContainers = new ArrayList<>();

        // hole creation time
        System.out.println("[INFO]: Create holes for " + inputImageNames.size() + " images (" + amount + " per image)");
        for (String fileName : inputImageNames) {
            System.out.println();
            System.out.println("[INFO]: Image name: " + fileName);
            String fullImagePath = datasetPath + subInputPath + "/" + fileName + "." + extension;
            BufferedImage image = iRW.readImageByFullPath(fullImagePath, true);
            int width = image.getWidth();
            int height = image.getHeight();
            System.out.println("[INFO]: Image width: " + width);
            System.out.println("[INFO]: Image height: " + height);

            for (int i = 0; i < amount; i++) {
                if (i != 0) {
                    image = iRW.readImageByFullPath(fullImagePath, false);
                }
                int holeHeight = hCU.generateRandomSize(minHeight, maxHeight);
                int holeWidth = hCU.generateRandomSize(minWidth, maxWidth);
                int startX = hCU.generateRandomCoordinate(width, holeWidth, width - holeWidth);
                int startY = hCU.generateRandomCoordinate(height, holeHeight, height - holeHeight);
                double holeArea = hCU.calculateFigureArea(holeHeight, holeWidth, holeForm);
                System.out.println("[INFO]: Hole №" + i + ":\n\t\tsize: " + holeWidth + "x" + holeHeight +
                        "\n\t\tcoordinate: (" + startX + ", " + startY + ")" +
                        "\n\t\tarea: " + holeArea);
                String outFileName = fileName + "_" + outPostfix + "_" + i;
                String maskFileName = fileName + "_" + outPostfix + "_mask_" + i;

                BufferedImage holeMask = hC.createColorHole(image,
                        startX,
                        startY,
                        holeWidth,
                        holeHeight,
                        argbColor,
                        holeForm,
                        width,
                        height
                );

                String outImageFullPath = datasetPath + subOutputPath + "/" + outFileName + "." + extension;
                iRW.writeImageByFullPath(image, outImageFullPath, extension);
                outputFileNames.add(outFileName);

                String maskImageFullPath = datasetPath + subMasksPath + "/" + maskFileName + "." + extension;
                iRW.writeImageByFullPath(holeMask, maskImageFullPath, extension);
                maskFileNames.add(maskFileName);

                // create statistic pojo
                StatisticContainer sC = new StatisticContainer();
                sC.setImageName(outFileName);
                sC.setMaskName(maskFileName);
                sC.setHoleHeight(holeHeight);
                sC.setHoleWidth(holeWidth);
                sC.setImageHeight(height);
                sC.setImageWidth(width);
                sC.setHoleForm(holeForm);
                sC.setHoleArea(holeArea);
                statisticContainers.add(sC);
            }
        }

        // write output flist
        String outputFlistFullPath = datasetPath + subOutputPath + "/" + outputFlistFileName;
        fu.writeFlistByFullPath(outputFlistFullPath, outputFileNames);

        // write mask flist
        String masksFlistFullPath = datasetPath + subMasksPath + "/" + masksFlistFileName;
        fu.writeFlistByFullPath(masksFlistFullPath, maskFileNames);

        // write output statistic
        String outputStatisticFullPath = datasetPath + statisticOutPutFileName;
        fu.writeStatisticByFullPath(outputStatisticFullPath, statisticContainers);
    }
}
