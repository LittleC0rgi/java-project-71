package hexlet.code;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {
    private static final int TIMEOUT = 50;

    @Test
    void testJsonParsing() throws Exception {
        Path file = Files.createTempFile("test", ".json");

        String content = """
                {
                  "host": "hexlet.io",
                  "timeout": %d,
                  "proxy": "123.234.53.22",
                  "follow": false
                }
                """.formatted(TIMEOUT);

        Files.writeString(file, content);

        Map<String, Object> result = Parser.getData(file);

        assertEquals("hexlet.io", result.get("host"));
        assertEquals(TIMEOUT, result.get("timeout"));
        assertEquals("123.234.53.22", result.get("proxy"));
        assertEquals(false, result.get("follow"));
    }

    @Test
    void testYamlParsing() throws Exception {
        Path file = Files.createTempFile("test", ".yaml");

        String content = """
                host: hexlet.io
                timeout: %d
                proxy: 123.234.53.22
                follow: false
                """.formatted(TIMEOUT);

        Files.writeString(file, content);

        Map<String, Object> result = Parser.getData(file);

        assertEquals("hexlet.io", result.get("host"));
        assertEquals(TIMEOUT, result.get("timeout"));
        assertEquals("123.234.53.22", result.get("proxy"));
        assertEquals(false, result.get("follow"));
    }
}
