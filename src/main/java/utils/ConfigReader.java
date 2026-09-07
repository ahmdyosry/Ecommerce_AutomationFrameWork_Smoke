package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input =
                     ConfigReader.class
                             .getClassLoader()
                             .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new RuntimeException(
                        "config.properties file not found"
                );
            }

            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to load config.properties",
                    e
            );
        }
    }

    private ConfigReader() {
    }

    public static String getProperty(String key) {

        String value = properties.getProperty(key);

        if (value == null) {
            throw new RuntimeException(
                    "Property not found: " + key
            );
        }

        return value;
    }
}