package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class configReader {

    private static final Properties properties;

    static {
        try {
            FileInputStream file = new FileInputStream("src/test/resources/config.properties");
            properties = new Properties();
            properties.load(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties file: " + e.getMessage());
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null){
            System.err.println("property Key not found : " +key);
        }
        return value;
    }
}
