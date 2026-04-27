package hexlet.code;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class Parser {

    public static Map<String, Object> getData(Path path) throws Exception {
        String content = readFile(path);
        String format = getFormat(path);
        return parse(content, format);
    }

    public static String readFile(Path path) throws Exception {
        return Files.readString(path);
    }

    public static Map<String, Object> parse(String content, String format) throws Exception {
        ObjectMapper mapper;

        if (format.equals("yaml") || format.equals("yml")) {
            mapper = new ObjectMapper(new YAMLFactory());
        } else {
            mapper = new ObjectMapper();
        }

        return mapper.readValue(content, Map.class);
    }

    private static String getFormat(Path path) {
        String fileName = path.getFileName().toString();
        int dotIndex = fileName.lastIndexOf(".");
        return dotIndex == -1 ? "" : fileName.substring(dotIndex + 1);
    }
}
