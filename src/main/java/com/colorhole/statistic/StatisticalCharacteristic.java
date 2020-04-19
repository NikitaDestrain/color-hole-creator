package com.colorhole.statistic;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.BigInteger;

public class StatisticalCharacteristic {

    private static StatisticalCharacteristic instance;

    private StatisticalCharacteristic() {
    }

    public static StatisticalCharacteristic getInstance() {
        if (instance == null) instance = new StatisticalCharacteristic();
        return instance;
    }

    public double calculateMSEForSimilarSizeImages(BufferedImage image1, BufferedImage image2) {
        int width = image1.getWidth();
        int height = image1.getHeight();

        double MSE = 0;
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                int argb1 = image1.getRGB(column, row);
                int argb2 = image2.getRGB(column, row);
                int argbDiff = argb1 - argb2;
                MSE += (double) argbDiff * argbDiff;
            }
        }

        MSE /= width * height;
        return MSE;
    }


    public Double psnr(BufferedImage i1, BufferedImage i2) {

        int cc1 = i1.getColorModel().getNumComponents();
        double bpp1 = i1.getColorModel().getPixelSize() / cc1;
        int cc2 = i2.getColorModel().getNumComponents();
        double bpp2 = i2.getColorModel().getPixelSize() / cc2;
        int cc = 4;
        if (cc1 == 3 && cc2 == 3) {
            cc = 3;
        }

        KahanSummation tr = new KahanSummation();
        KahanSummation tg = new KahanSummation();
        KahanSummation tb = new KahanSummation();
        KahanSummation ta = new KahanSummation();
        BigInteger br = BigInteger.valueOf(0);
        BigInteger bg = BigInteger.valueOf(0);
        BigInteger bb = BigInteger.valueOf(0);
        BigInteger ba = BigInteger.valueOf(0);

        for (int i = 0; i < i1.getWidth(); i++) {
            for (int j = 0; j < i1.getHeight(); j++) {
                final Color c1 = new Color(i1.getRGB(i, j));
                final Color c2 = new Color(i2.getRGB(i, j));
                final int dr = c1.getRed() - c2.getRed();
                final int dg = c1.getGreen() - c2.getGreen();
                final int db = c1.getBlue() - c2.getBlue();
                final int da = c1.getAlpha() - c2.getAlpha();
                tr.add(dr * dr);
                tg.add(dg * dg);
                tb.add(db * db);
                ta.add(da * da);
                br = br.add(BigInteger.valueOf(dr * dr));
                bg = bg.add(BigInteger.valueOf(dg * dg));
                bb = bb.add(BigInteger.valueOf(db * db));
                ba = ba.add(BigInteger.valueOf(da * da));
            }
        }

        double mse = (tr.getSum() + tg.getSum() + tb.getSum() + ta.getSum()) / (i1.getWidth() * i1.getHeight() * cc);
        System.out.println("Mean square error: " + mse);
        System.out.println("Mean square error 2: " + (br.doubleValue() + bg.doubleValue() + bb.doubleValue() + ba.doubleValue()) / (i1.getWidth() * i1.getHeight() * cc));
        if (mse == 0) {
            System.out.println("mse == 0 and so psnr will be infinity!");
        }

        System.out.println("Got: br = " + br + ", tr = " + tr.getSum());
        System.out.println("Got: bg = " + bg + ", tg = " + tg.getSum());
        System.out.println("Got: bb = " + bb + ", tb = " + tb.getSum());
        System.out.println("Got: ba = " + ba + ", ta = " + ta.getSum());
        BigDecimal bmse = new BigDecimal("0.00");
        bmse = bmse.add(new BigDecimal(br));
        bmse = bmse.add(new BigDecimal(bg));
        bmse = bmse.add(new BigDecimal(bb));
        bmse = bmse.add(new BigDecimal(ba));
        bmse = new BigDecimal(bmse.doubleValue() / (i1.getWidth() * i1.getHeight() * cc));
        System.out.println("bmse = " + bmse);

        if (bpp1 != bpp2) {
            System.out.println("Bits-per-pixel do not match up! bpp1 = " + bpp1 + ", bpp2 = " + bpp2);
        }
        double bpp = bpp1;
        System.out.println("read bpp = " + bpp);
        System.out.println("colcomp = " + cc);

        bpp = 8;

        double max = Math.pow(2.0, bpp) - 1.0;

        double psnr = 10.0 * StrictMath.log10(max * max / mse);
        System.out.println("Peak signal to noise ratio: " + psnr); //43.82041101171352
        System.out.println("Peak signal to noise ratio (BigDecimal): " + 10.0 * StrictMath.log10(max * max / bmse.doubleValue()));

        return new Double(psnr);
    }
}
