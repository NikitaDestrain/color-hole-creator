package com.colorhole;

import com.colorhole.hole.HoleCreator;
import com.colorhole.hole.HoleCreatorUtils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HoleCreatorRunner {

    public static void main(String args[]) {


        ImageRW imageRW = ImageRW.getInstance();
        List<String> imagePathList = imageRW.getNameListForPath(Constants.IMAGENET_IMAGE_PATH, "helper");
        HoleCreator holeCreator = HoleCreator.getInstance();
        HoleCreatorUtils holeCreatorUtils = HoleCreatorUtils.getInstance();
        for (String name : imagePathList) {
            System.out.println("[INFO]: Image name: " + name);
            BufferedImage image = imageRW.readImageByFullPath(Constants.IMAGENET_IMAGE_PATH + name);
            int height = image.getHeight();
            int width = image.getWidth();
            System.out.println("[INFO]: Image height: " + height);
            System.out.println("[INFO]: Image width: " + width);

            int holeHeight = height / Constants.HOLE_PART_OF_FULL_IMAGE;
            int holeWidth = width / Constants.HOLE_PART_OF_FULL_IMAGE;
            System.out.println("[INFO]: Hole height (1/6 of image height): " + holeHeight);
            System.out.println("[INFO]: Hole width (1/6 of image width): " + holeWidth);

            int startX = holeCreatorUtils.generateRandomCoordinate(width, width - holeWidth);
            int startY = holeCreatorUtils.generateRandomCoordinate(height, height - holeHeight);

            holeCreator.createColorHole(image, startX, startY, holeWidth, holeHeight, Constants.GREEN_COLOR_ARGB);
            imageRW.writeImageByFullPath(image, Constants.IMAGENET_OUT_IMAGE_PATH + name, Constants.PNG_FORMAT);
        }
    }
}