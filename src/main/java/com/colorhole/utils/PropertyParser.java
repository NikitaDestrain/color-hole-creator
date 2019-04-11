package com.colorhole.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyParser {
    private static String PATH_TO_PROPERTY = "properties/";
    private static Properties props;

    public PropertyParser(String propertyName) throws IOException {
        this.props = new Properties();
        FileInputStream fin = new FileInputStream(PATH_TO_PROPERTY + propertyName);
        this.props.load(fin);
        fin.close();
    }

    public String getProperty(String key) throws NotFoundPropertyException {
        String prop = props.getProperty(key);
        if (prop == null) {
            throw new NotFoundPropertyException("Property " + key + " was not found in property file.");
        }
        return prop;
    }
}

