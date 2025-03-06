package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

import java.io.IOException;
import java.util.Map;

public class jsonReader {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, String> readJsonAsMap(String filepath) throws IOException{

        return objectMapper.readValue(new File(filepath), new TypeReference<Map<String, String>>() {
        });
    };

}
