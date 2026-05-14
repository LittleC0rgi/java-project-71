package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.util.Map;

public class Parser {
    public static Map<String, Object> parse(String data, String format) throws Exception {
        ObjectMapper mapper = getMapper(format);
        return mapper.readValue(data, Map.class);
    }

    private static ObjectMapper getMapper(String format) throws Exception {
        return switch (format) {
            case "yaml", "yml" -> new ObjectMapper(new YAMLFactory());
            case "json" -> new ObjectMapper();
            default -> throw new Exception("Unknown format: '" + format + "'");
        };
    }
}
