package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public class JsonDataReader {

    public static List<HashMap<String, Object>> getJsonData(String resourcePath)
            throws IOException {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(resourcePath)) {

            if (inputStream == null) {
                throw new FileNotFoundException("JSON resource not found: " + resourcePath);
            }

            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(inputStream, new TypeReference<List<HashMap<String, Object>>>() {});
        }
    }
}