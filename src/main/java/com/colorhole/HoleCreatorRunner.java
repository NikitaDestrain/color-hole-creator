package com.colorhole;

import com.colorhole.hole.HoleCreator;
import com.colorhole.hole.HoleCreatorUtils;

import java.awt.image.BufferedImage;

public class HoleCreatorRunner {

    public static void main(String args[]) {
        ImageRW imageRW = ImageRW.getInstance();
        HoleCreator holeCreator = HoleCreator.getInstance();
        HoleCreatorUtils holeCreatorUtils = HoleCreatorUtils.getInstance();

        BufferedImage image = imageRW.readImage("example.jpg");
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

        holeCreator.createColorHole(image, startX, startY, holeWidth, holeHeight, Constants.BLACK_COLOR_ARGB);
        imageRW.writeImage(image, "black-hole-example.jpg");
    }
}