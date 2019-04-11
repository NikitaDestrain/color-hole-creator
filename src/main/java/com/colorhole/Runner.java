package com.colorhole;

import com.colorhole.hole.ColorConstants;
import com.colorhole.hole.HoleCreator;
import com.colorhole.hole.HoleCreatorUtils;
import com.colorhole.utils.FileUtils;
import com.colorhole.utils.ImageRW;
import com.colorhole.utils.PropertyConstants;
import com.colorhole.utils.PropertyParser;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Runner {

    public static void main(String[] args) throws Exception {
        PropertyParser runProperties = new PropertyParser("configuration.properties");
        FileUtils fu = FileUtils.getInstance();
        ImageRW iRW = ImageRW.getInstance();
        HoleCreator hC = HoleCreator.getInstance();
        HoleCreatorUtils hCU = HoleCreatorUtils.getInstance();

        // process config file
        String subPath = runProperties.getProperty(PropertyConstants.PATH_PROPERTY);
        int amount = Integer.parseInt(runProperties.getProperty(PropertyConstants.AMOUNT_PROPERTY));
        String outPostfix = runProperties.getProperty(PropertyConstants.OUTPUT_POSTFIX_PROPERTY);
        String form = runProperties.getProperty(PropertyConstants.FORM_PROPERTY);
        int minHeight = Integer.parseInt(runProperties.getProperty(PropertyConstants.MIN_HEIGHT_PROPERTY));
        int maxHeight = Integer.parseInt(runProperties.getProperty(PropertyConstants.MAX_HEIGHT_PROPERTY));
        int minWidth = Integer.parseInt(runProperties.getProperty(PropertyConstants.MIN_WIDTH_PROPERTY));
        int maxWidth = Integer.parseInt(runProperties.getProperty(PropertyConstants.MAX_WIDTH_PROPERTY));
        String color = runProperties.getProperty(PropertyConstants.COLOR_PROPERTY);
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

        // process descriptor
        String datasetPath = RunnerConstants.IMG_PATH_FILE_NAME + subPath + "/";
        String extension = fu.readFileToString(datasetPath, RunnerConstants.DESCRIPTOR_FILE_NAME);
        // process file names file
        List<String> inputImageNames = fu.getNameListForPath(datasetPath, RunnerConstants.FLIST_FILE_NAME);
        List<String> outputFileNames = new ArrayList<>();

        // hole creation time
        System.out.println("[INFO]: Create holes for " + inputImageNames.size() + " images (" + amount + " per image)");
        for (String fileName : inputImageNames) {
            System.out.println();
            System.out.println("[INFO]: Image name: " + fileName);
            String fullImagePath = datasetPath + fileName + "." + extension;
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
                int startX = hCU.generateRandomCoordinate(width, width - holeWidth);
                int startY = hCU.generateRandomCoordinate(height, height - holeHeight);
                System.out.println("[INFO]: Hole №" + i + ":\n\t\tsize: " + width + "x" + height +
                        "\n\t\tcoordinate: (" + startX + ", " + startY + ")");
                hC.createColorHole(image, startX, startY, holeWidth, holeHeight, argbColor);

                String outFileName = fileName + "_" + outPostfix + "_" + i;
                String outImageFullPath = datasetPath + outFileName + "." + extension;
                iRW.writeImageByFullPath(image, outImageFullPath, extension);
                outputFileNames.add(outFileName);
            }
        }

        // write output flist
        String outputFlistFullPath = datasetPath + RunnerConstants.FLIST_HOLE_FILE_NAME;
        fu.writeFlistByFullPath(outputFlistFullPath, outputFileNames);
    }
}
