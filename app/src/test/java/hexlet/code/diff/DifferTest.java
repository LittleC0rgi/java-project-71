package hexlet.code.diff;

import hexlet.code.formatter.FormatType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifferTest {
    private Map<String, Object> map(Object... entries) {
        Map<String, Object> result = new HashMap<>();
        for (int i = 0; i < entries.length; i += 2) {
            result.put((String) entries[i], entries[i + 1]);
        }
        return result;
    }

    @Test
    void testEmptyMaps() {
        var result = Differ.generate(new HashMap<>(), new HashMap<>());
        assertEquals("{\n\n}", result);
    }

    @Test
    void testIdenticalMaps() {
        var data1 = map("host", "hexlet.io", "timeout", 50);

        var data2 = map("host", "hexlet.io", "timeout", 50);

        var result = Differ.generate(data1, data2);

        String expected = """
                {
                    host: hexlet.io
                    timeout: 50
                }""";

        assertEquals(expected, result);
    }

    @Test
    void testRemovedKey() {
        var data1 = map("host", "hexlet.io", "timeout", 50);

        var data2 = map("host", "hexlet.io");

        var result = Differ.generate(data1, data2);

        String expected = """
                {
                    host: hexlet.io
                  - timeout: 50
                }""";

        assertEquals(expected, result);
    }

    @Test
    void testAddedKey() {
        var data1 = map("host", "hexlet.io");

        var data2 = map("host", "hexlet.io", "timeout", 50);

        var result = Differ.generate(data1, data2);

        String expected = """
                {
                    host: hexlet.io
                  + timeout: 50
                }""";

        assertEquals(expected, result);
    }

    @Test
    void testChangedValue() {
        var data1 = map("timeout", 50);

        var data2 = map("timeout", 20);

        var result = Differ.generate(data1, data2);

        String expected = """
                {
                  - timeout: 50
                  + timeout: 20
                }""";

        assertEquals(expected, result);
    }

    @Test
    void testMixedCase() {
        var data1 = map("host", "hexlet.io", "timeout", 50, "proxy", "123.234.53.22");

        var data2 = map("timeout", 20, "verbose", true, "host", "hexlet.io");

        var result = Differ.generate(data1, data2, FormatType.STYLISH);

        String expected = """
                {
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";

        assertEquals(expected, result);
    }

    @Test
    void testPlainFormat() {
        var data1 = map("host", "hexlet.io", "timeout", 50, "proxy", "123.234.53.22");

        var data2 = map("timeout", 20, "verbose", true, "host", "hexlet.io");

        var result = Differ.generate(data1, data2, FormatType.PLAIN);

        String expected = """
                Property 'proxy' was removed
                Property 'timeout' was updated. From 50 to 20
                Property 'verbose' was added with value: true
                """;

        assertEquals(expected, result);
    }

    @Test
    void testJsonFormat() {
        var data1 = map("host", "hexlet.io", "timeout", 50, "proxy", "123.234.53.22");

        var data2 = map("timeout", 20, "verbose", true, "host", "hexlet.io");

        var result = Differ.generate(data1, data2, FormatType.JSON);

        String expected = """
                {
                  "host": {
                    "status": "unchanged",
                    "value": "hexlet.io"
                  },
                  "proxy": {
                    "status": "removed",
                    "value": "123.234.53.22"
                  },
                  "timeout": {
                    "status": "updated",
                    "oldValue": 50,
                    "newValue": 20
                  },
                  "verbose": {
                    "status": "added",
                    "value": true
                  }
                }""";
        
        assertEquals(expected, result);
    }
}
