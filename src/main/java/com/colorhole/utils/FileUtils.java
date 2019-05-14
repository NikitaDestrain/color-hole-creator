package com.colorhole.utils;

import com.colorhole.statistic.MseStatisticContainer;
import com.colorhole.statistic.StatisticContainer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtils {

    private static final String CSV_STATISTICS_HEADER = "imageName,maskName,imageHeight,imageWidth,holeHeight,holeWidth,holeForm,holeArea";
    private static final String CSV_STATISTICS_MSE_HEADER = "originalImageName,imageHeight,imageWidth,amount,mse";
    private static final String COMMA_DELIMITER = ",";
    private static final String LINE_SEPARATOR = "\n";

    private static FileUtils instance;

    private FileUtils() {
    }

    public static FileUtils getInstance() {
        if (instance == null) instance = new FileUtils();
        return instance;
    }

    // todo refactor
    public String readFileToString(String path, String name) throws FileNotFoundException {
        String inFullPath = path + name;
        return new Scanner(new File(inFullPath)).useDelimiter("\\Z").next();
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

    public void writeFlistByFullPath(String path, List<String> list) {
        try (PrintWriter pw = new PrintWriter(path)) {
            for (String name : list) {
                pw.write(name);
                pw.write("\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: Output file can not be created! Please, check stack trace logs:");
            e.printStackTrace();
        }
    }

    public void writeStatisticByFullPath(String fullPath, List<StatisticContainer> statisticContainers) {
        try (FileWriter fileWriter = new FileWriter(fullPath)) {
            fileWriter.append(CSV_STATISTICS_HEADER);
            fileWriter.append(LINE_SEPARATOR);
            for (StatisticContainer statisticContainer : statisticContainers) {
                fileWriter.append(String.valueOf(statisticContainer.getImageName()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(statisticContainer.getMaskName()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(statisticContainer.getImageHeight()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(statisticContainer.getImageWidth()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(statisticContainer.getHoleHeight()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(statisticContainer.getHoleWidth()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(statisticContainer.getHoleForm()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(statisticContainer.getHoleArea()));
                fileWriter.append(LINE_SEPARATOR);
            }
            System.out.println("[INFO]: Write to CSV file succeeded! See result: " + fullPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeMSEStatisticByFullPath(String fullPath, List<MseStatisticContainer> mseStatisticContainers) {
        try (FileWriter fileWriter = new FileWriter(fullPath)) {
            fileWriter.append(CSV_STATISTICS_MSE_HEADER);
            fileWriter.append(LINE_SEPARATOR);
            for (MseStatisticContainer statisticContainer : mseStatisticContainers) {
                fileWriter.append(String.valueOf(statisticContainer.getOriginalImageName()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(statisticContainer.getImageHeight()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(statisticContainer.getImageWidth()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(statisticContainer.getAmount()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(statisticContainer.getMse()));
                fileWriter.append(LINE_SEPARATOR);
            }
            System.out.println("[INFO]: Write to CSV file succeeded! See result: " + fullPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
