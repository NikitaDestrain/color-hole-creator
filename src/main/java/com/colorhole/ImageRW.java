package com.colorhole;

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
            File imageFile = new File(Constants.SOURCE_IMAGE_PATH + imageName);
            BufferedImage image = ImageIO.read(imageFile);
            System.out.println("[INFO]: Reading " + imageName + " complete");
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // todo catch block refactor
    public BufferedImage readImageByFullPath(String fullImagePath) {
        try {
            File imageFile = new File(fullImagePath);
            BufferedImage image = ImageIO.read(imageFile);
            System.out.println("[INFO]: Reading " + fullImagePath + " complete");
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // todo catch block refactor
    public void writeImage(BufferedImage image, String imageName, String format) {
        try {
            File imageFile = new File(Constants.TARGET_IMAGE_PATH + imageName);
            ImageIO.write(image, format, imageFile);
            System.out.println("[INFO]: Writing " + imageName + " complete");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // todo catch block refactor
    public void writeImageByFullPath(BufferedImage image, String fullImagePath, String format) {
        try {
            File imageFile = new File(fullImagePath);
            ImageIO.write(image, format, imageFile);
            System.out.println("[INFO]: Writing " + fullImagePath + " complete");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getNameListForPath(String path, String fileName) {
        List<String> imageNames = new ArrayList<>();
        try {
            FileInputStream fStream = new FileInputStream(path + fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(fStream));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                imageNames.add(strLine);
            }
            br.close();
            fStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageNames;
    }
}
