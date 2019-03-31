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

    public int generateRandomCoordinate(int max, int bound) {
        int coord = 0;
        if (bound != -1) {
            boolean isValidY = false;
            while (!isValidY) {
                coord = random.nextInt(max);
                if (coord < bound) {
                    isValidY = true;
                }
            }
        } else {
            coord = random.nextInt(max);
        }
        return coord;
    }
}
