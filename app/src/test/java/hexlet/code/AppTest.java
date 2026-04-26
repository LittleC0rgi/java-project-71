package hexlet.code;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {

    private Map<String, Object> map(Object... entries) {
        Map<String, Object> result = new HashMap<>();
        for (int i = 0; i < entries.length; i += 2) {
            result.put((String) entries[i], entries[i + 1]);
        }
        return result;
    }

    @Test
    void testEmptyMaps() {
        var result = App.buildDiff(new HashMap<>(), new HashMap<>());
        assertEquals("{\n\n}", result);
    }

    @Test
    void testIdenticalMaps() {
        var data1 = map("host", "hexlet.io", "timeout", 50);

        var data2 = map("host", "hexlet.io", "timeout", 50);

        var result = App.buildDiff(data1, data2);

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

        var result = App.buildDiff(data1, data2);

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

        var result = App.buildDiff(data1, data2);

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

        var result = App.buildDiff(data1, data2);

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

        var result = App.buildDiff(data1, data2);

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
}
