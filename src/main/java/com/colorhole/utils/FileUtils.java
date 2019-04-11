package com.colorhole.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtils {

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
}
