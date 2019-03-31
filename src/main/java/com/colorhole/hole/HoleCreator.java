package com.colorhole.hole;

import java.awt.image.BufferedImage;

public class HoleCreator {

    private static HoleCreator instance;

    private HoleCreator() {
    }

    public static HoleCreator getInstance() {
        if (instance == null) instance = new HoleCreator();
        return instance;
    }

    public void createColorHole(BufferedImage image, int holeX, int holeY, int holeWidth, int holeHeight, int holeColor) {
        int maxX = holeX + holeHeight;
        int maxY = holeY + holeWidth;
        for (int row = holeY; row < maxY; row++) {
            for (int column = holeX; column < maxX; column++) {
                image.setRGB(column, row, holeColor);
            }
        }
        System.out.println("[INFO]: Hole " + holeWidth + "x" + holeHeight + " with color " + holeColor + " has been created");
    }
}
