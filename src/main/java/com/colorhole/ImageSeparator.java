package com.colorhole;

import java.awt.image.BufferedImage;

public class ImageSeparator {

    public static void main(String[] args) {

        ImageRW imageRW = ImageRW.getInstance();
        BufferedImage image = imageRW.readImageByFullPath(Constants.SOURCE_IMAGE_PATH + "smailik1.png");

        int width = image.getWidth() / 3;
        int height = image.getHeight();


        System.out.println(image.getType());
    }
}
