package com.colorhole.hole;

import java.awt.image.BufferedImage;

public class HoleCreator {

    public enum HoleForm {
        RECTANGLE,
        ELLIPSE
    }

    private static HoleCreator instance;

    private HoleCreator() {
    }

    public static HoleCreator getInstance() {
        if (instance == null) instance = new HoleCreator();
        return instance;
    }

    public void createColorHole(BufferedImage image,
                                int holeX,
                                int holeY,
                                int holeWidth,
                                int holeHeight,
                                int holeColor,
                                HoleForm holeForm) {
        switch (holeForm) {
            case RECTANGLE: {
                int maxX = holeX + holeWidth;
                int maxY = holeY + holeHeight;
                for (int row = holeY; row < maxY; row++) {
                    for (int column = holeX; column < maxX; column++) {
                        image.setRGB(column, row, holeColor);
                    }
                }
                break;
            }
            case ELLIPSE: {
                // (holeX, holeY) = center
                int a = holeWidth / 2;
                int b = holeHeight / 2;

                // color time
                int deltaX = 0;
                int deltaY = b;
                int a_sqr = a * a;
                int b_sqr = b * b;
                int delta = 4 * b_sqr * ((deltaX + 1) * (deltaX + 1)) + a_sqr * ((2 * deltaY - 1) * (2 * deltaY - 1)) - 4 * a_sqr * b_sqr;
                while (a_sqr * (2 * deltaY - 1) > 2 * b_sqr * (deltaX + 1)) {
                    createEllipsePixels(image, holeX, holeY, deltaX, deltaY, holeColor);
                    if (delta < 0) {
                        deltaX++;
                        delta += 4 * b_sqr * (2 * deltaX + 3);
                    } else {
                        deltaX++;
                        delta = delta - 8 * a_sqr * (deltaY - 1) + 4 * b_sqr * (2 * deltaX + 3);
                        deltaY--;
                    }
                }
                delta = b_sqr * ((2 * deltaX + 1) * (2 * deltaX + 1)) + 4 * a_sqr * ((deltaY + 1) * (deltaY + 1)) - 4 * a_sqr * b_sqr;
                while (deltaY + 1 != 0) {
                    createEllipsePixels(image, holeX, holeY, deltaX, deltaY, holeColor);
                    if (delta < 0) {
                        deltaY--;
                        delta += 4 * a_sqr * (2 * deltaY + 3);
                    } else {
                        deltaY--;
                        delta = delta - 8 * b_sqr * (deltaX + 1) + 4 * a_sqr * (2 * deltaY + 3);
                        deltaX++;
                    }
                }
                break;
            }
            default: {
                break;
            }
        }
        System.out.println("[INFO]: Hole (" + holeForm.toString() + ") " +
                holeWidth + "x" + holeHeight +
                " with color " + holeColor + " has been created");
    }

    private void createEllipsePixels(BufferedImage image, int x, int y, int deltaX, int deltaY, int color) {
        int rightDownX = x + deltaX;
        int rightDownY = y + deltaY;

        int rightUpX = x + deltaX;
        int rightUpY = y - deltaY;

        int leftUpX = x - deltaX;
        int leftUpY = y - deltaY;

        int leftDownX = x - deltaX;
        int leftDownY = y + deltaY;

        for (int i = x; i < rightDownX; i++) {
            image.setRGB(i, rightDownY, color); // right down
        }

        for (int i = x; i < rightUpX; i++) {
            image.setRGB(i, rightUpY, color); // right down
        }

        for (int i = leftUpX; i < x; i++) {
            image.setRGB(i, leftUpY, color); // right down
        }

        for (int i = leftDownX; i < x; i++) {
            image.setRGB(i, leftDownY, color); // right down
        }
    }
}
