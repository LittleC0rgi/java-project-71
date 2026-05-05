package hexlet.code.diff;

import hexlet.code.Differ;
import hexlet.code.formatter.FormatType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifferTest {
    private static final int TIMEOUT_OLD = 50;
    private static final int TIMEOUT_NEW = 20;

    private Map<String, Object> map(Object... entries) {
        Map<String, Object> result = new HashMap<>();
        for (int i = 0; i < entries.length; i += 2) {
            result.put((String) entries[i], entries[i + 1]);
        }
        return result;
    }

    @Test
    void testEmptyMaps() throws Exception {
        var result = Differ.generate(new HashMap<>(), new HashMap<>());
        assertEquals("{\n\n}", result);
    }

    @Test
    void testIdenticalMaps() throws Exception {
        var data1 = map("host", "hexlet.io", "timeout", TIMEOUT_OLD);
        var data2 = map("host", "hexlet.io", "timeout", TIMEOUT_OLD);
        var result = Differ.generate(data1, data2);
        String expected = """
                {
                    host: hexlet.io
                    timeout: %d
                }""".formatted(TIMEOUT_OLD);
        assertEquals(expected, result);
    }

    @Test
    void testRemovedKey() throws Exception {
        var data1 = map("host", "hexlet.io", "timeout", TIMEOUT_OLD);
        var data2 = map("host", "hexlet.io");
        var result = Differ.generate(data1, data2);
        String expected = """
                {
                    host: hexlet.io
                  - timeout: %d
                }""".formatted(TIMEOUT_OLD);
        assertEquals(expected, result);
    }

    @Test
    void testAddedKey() throws Exception {
        var data1 = map("host", "hexlet.io");
        var data2 = map("host", "hexlet.io", "timeout", TIMEOUT_OLD);
        var result = Differ.generate(data1, data2);
        String expected = """
                {
                    host: hexlet.io
                  + timeout: %d
                }""".formatted(TIMEOUT_OLD);
        assertEquals(expected, result);
    }

    @Test
    void testChangedValue() throws Exception {
        var data1 = map("timeout", TIMEOUT_OLD);
        var data2 = map("timeout", TIMEOUT_NEW);
        var result = Differ.generate(data1, data2);
        String expected = """
                {
                  - timeout: %d
                  + timeout: %d
                }""".formatted(TIMEOUT_OLD, TIMEOUT_NEW);
        assertEquals(expected, result);
    }

    @Test
    void testMixedCase() throws Exception {
        var data1 = map("host", "hexlet.io", "timeout", TIMEOUT_OLD, "proxy", "123.234.53.22");
        var data2 = map("timeout", TIMEOUT_NEW, "verbose", true, "host", "hexlet.io");
        var result = Differ.generate(data1, data2, FormatType.STYLISH);
        String expected = """
                {
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: %d
                  + timeout: %d
                  + verbose: true
                }""".formatted(TIMEOUT_OLD, TIMEOUT_NEW);
        assertEquals(expected, result);
    }

    @Test
    void testPlainFormat() throws Exception {
        var data1 = map("host", "hexlet.io", "timeout", TIMEOUT_OLD, "proxy", "123.234.53.22");
        var data2 = map("timeout", TIMEOUT_NEW, "verbose", true, "host", "hexlet.io");
        var result = Differ.generate(data1, data2, FormatType.PLAIN);
        String expected = """
                Property 'proxy' was removed
                Property 'timeout' was updated. From %d to %d
                Property 'verbose' was added with value: true""".formatted(TIMEOUT_OLD, TIMEOUT_NEW);
        assertEquals(expected, result);
    }

    @Test
    void testJsonFormat() throws Exception {
        var data1 = map("host", "hexlet.io", "timeout", TIMEOUT_OLD, "proxy", "123.234.53.22");
        var data2 = map("timeout", TIMEOUT_NEW, "verbose", true, "host", "hexlet.io");
        var result = Differ.generate(data1, data2, FormatType.JSON);
        String expected = """
                {
                  "host" : {
                    "status" : "unchanged",
                    "value" : "hexlet.io"
                  },
                  "proxy" : {
                    "status" : "removed",
                    "value" : "123.234.53.22"
                  },
                  "timeout" : {
                    "status" : "updated",
                    "oldValue" : %d,
                    "newValue" : %d
                  },
                  "verbose" : {
                    "status" : "added",
                    "value" : true
                  }
                }""".formatted(TIMEOUT_OLD, TIMEOUT_NEW);
        assertEquals(expected, result);
    }
}
