package hexlet.code;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class DifferTest {
    private static String getFilePath(String filename) {
        return Path.of("src/test/resources/files/json/" + filename)
                .toAbsolutePath()
                .toString();
    }

    private static String readExpected(String filename) throws Exception {
        return Files.readString(
                Path.of("src/test/resources/expected/" + filename)
        ).strip();
    }

    @Test
    void testGenerateStylish() throws Exception {
        String result = Differ.generate(
                getFilePath("file1.json"),
                getFilePath("file2.json"),
                "stylish"
        );
        assertEquals(readExpected("expected_stylish.txt"), result);
    }

    @Test
    void testGeneratePlain() throws Exception {
        String result = Differ.generate(
                getFilePath("file1.json"),
                getFilePath("file2.json"),
                "plain"
        );
        assertEquals(readExpected("expected_plain.txt"), result);
    }

    @Test
    void testGenerateJson() throws Exception {
        String result = Differ.generate(
                getFilePath("file1.json"),
                getFilePath("file2.json"),
                "json"
        );
        assertEquals(readExpected("expected_json.txt"), result);
    }

    @Test
    void testGenerateInvalidFormat() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> Differ.generate(
                        getFilePath("file1.json"),
                        getFilePath("file2.json"),
                        "xml"
                )
        );
        assertTrue(ex.getMessage().contains("Unknown format: xml"));
    }
}
