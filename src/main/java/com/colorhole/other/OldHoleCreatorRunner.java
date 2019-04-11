package com.colorhole.other;

import com.colorhole.hole.ColorConstants;
import com.colorhole.hole.HoleCreator;
import com.colorhole.hole.HoleCreatorUtils;
import com.colorhole.utils.FileUtils;
import com.colorhole.utils.ImageRW;

import java.awt.image.BufferedImage;
import java.util.List;

@Deprecated
public class OldHoleCreatorRunner {

    public static void main(String args[]) {
        ImageRW imageRW = ImageRW.getInstance();
        FileUtils fu = FileUtils.getInstance();
        List<String> imagePathList = fu.getNameListForPath(OtherConstants.IMAGENET_IMAGE_PATH, "helper");
        HoleCreator holeCreator = HoleCreator.getInstance();
        HoleCreatorUtils holeCreatorUtils = HoleCreatorUtils.getInstance();

        for (String name : imagePathList) {
            System.out.println("[INFO]: Image name: " + name);
            BufferedImage image = imageRW.readImageByFullPath(OtherConstants.IMAGENET_IMAGE_PATH + name, true);
            int height = image.getHeight();
            int width = image.getWidth();
            System.out.println("[INFO]: Image height: " + height);
            System.out.println("[INFO]: Image width: " + width);

            int holeHeight = height / OtherConstants.HOLE_PART_OF_FULL_IMAGE;
            int holeWidth = width / OtherConstants.HOLE_PART_OF_FULL_IMAGE;
            System.out.println("[INFO]: Hole height (1/6 of image height): " + holeHeight);
            System.out.println("[INFO]: Hole width (1/6 of image width): " + holeWidth);

            int startX = holeCreatorUtils.generateRandomCoordinate(width, width - holeWidth);
            int startY = holeCreatorUtils.generateRandomCoordinate(height, height - holeHeight);

            holeCreator.createColorHole(image, startX, startY, holeWidth, holeHeight, ColorConstants.GREEN_COLOR_ARGB);
            imageRW.writeImageByFullPath(image, OtherConstants.IMAGENET_OUT_IMAGE_PATH + name, OtherConstants.PNG_FORMAT);
        }
    }
}