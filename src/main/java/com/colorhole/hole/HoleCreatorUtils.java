package com.colorhole.hole;

import java.util.Random;

public class HoleCreatorUtils {

    private static HoleCreatorUtils instance;
    private Random random;

    private HoleCreatorUtils() {
        this.random = new Random();
    }

    public static HoleCreatorUtils getInstance() {
        if (instance == null) instance = new HoleCreatorUtils();
        return instance;
    }

    // 0xaarrggbb - format
    public int convertToARGB(int a, int r, int g, int b) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    public int generateRandomCoordinate(int max,int bound1, int bound2) {
        int coord = 0;
        if (bound2 != -1) {
            boolean isValidY = false;
            while (!isValidY) {
                coord = random.nextInt(max);
                if (coord < bound2 && coord > bound1) {
                    isValidY = true;
                }
            }
        } else {
            coord = random.nextInt(max);
        }
        return coord;
    }

    public int generateRandomSize(int min, int max) {
        return min + random.nextInt(max);
    }

    public double calculateFigureArea(int height, int width, HoleCreator.HoleForm holeForm) {
        switch (holeForm) {
            case ELLIPSE:
                return (height * width * 0.25) * Math.PI;
            case RECTANGLE:
                return height * width;
            default:
                return height * width;
        }
    }
}
