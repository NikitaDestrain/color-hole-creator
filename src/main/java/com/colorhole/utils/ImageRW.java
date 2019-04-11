package com.colorhole.utils;

import com.colorhole.other.OtherConstants;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ImageRW {

    private static ImageRW instance;

    private ImageRW() {
    }

    public static ImageRW getInstance() {
        if (instance == null) instance = new ImageRW();
        return instance;
    }

    // todo catch block refactor
    public BufferedImage readImage(String imageName) {
        try {
            File imageFile = new File(OtherConstants.SOURCE_IMAGE_PATH + imageName);
            BufferedImage image = ImageIO.read(imageFile);
            System.out.println("[INFO]: Reading " + imageName + " completed");
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // todo catch block refactor
    public BufferedImage readImageByFullPath(String fullImagePath, boolean showInfo) {
        try {
            File imageFile = new File(fullImagePath);
            BufferedImage image = ImageIO.read(imageFile);
            if (showInfo) {
                System.out.println("[INFO]: Reading " + fullImagePath + " completed");
            }
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // todo catch block refactor
    public void writeImage(BufferedImage image, String imageName, String format) {
        try {
            File imageFile = new File(OtherConstants.TARGET_IMAGE_PATH + imageName);
            ImageIO.write(image, format, imageFile);
            System.out.println("[INFO]: Writing " + imageName + " completed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // todo catch block refactor
    public void writeImageByFullPath(BufferedImage image, String fullImagePath, String format) {
        try {
            File imageFile = new File(fullImagePath);
            ImageIO.write(image, format, imageFile);
            System.out.println("[INFO]: Writing " + fullImagePath + " completed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
