package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public class JsonDataReader {

    public static List<HashMap<String, Object>> getJsonData(String filePath)
            throws IOException {

        String jsonContent =
                FileUtils.readFileToString(
                        new File(filePath),
                        StandardCharsets.UTF_8
                );

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(
                jsonContent,
                new TypeReference<List<HashMap<String, Object>>>() {}
        );
    }
}