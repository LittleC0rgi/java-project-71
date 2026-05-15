package hexlet.code;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DifferTest {
    private static String getJsonFilePath(String filename) {
        return Path.of("src/test/resources/files/json/" + filename)
                .toAbsolutePath()
                .toString();
    }

    private static String getYamlFilePath(String filename) {
        return Path.of("src/test/resources/files/yaml/" + filename)
                .toAbsolutePath()
                .toString();
    }

    private static String readExpected(String filename) throws Exception {
        return Files.readString(
                Path.of("src/test/resources/expected/" + filename)).strip();
    }

    @Test
    void testGenerateJsonFromYaml() throws Exception {
        String result = Differ.generate(
                getYamlFilePath("file1.yaml"),
                getYamlFilePath("file2.yaml"),
                "json");
        assertEquals(readExpected("expected_json.txt"), result);
    }

    @Test
    void testGenerateJsonFromJson() throws Exception {
        String result = Differ.generate(
                getJsonFilePath("file1.json"),
                getJsonFilePath("file2.json"),
                "json");
        assertEquals(readExpected("expected_json.txt"), result);
    }

    @Test
    void testGenerateStylishFromYaml() throws Exception {
        String result = Differ.generate(
                getYamlFilePath("file1.yaml"),
                getYamlFilePath("file2.yaml"),
                "stylish");
        assertEquals(readExpected("expected_stylish.txt"), result);
    }

    @Test
    void testGenerateStylishFromJson() throws Exception {
        String result = Differ.generate(
                getJsonFilePath("file1.json"),
                getJsonFilePath("file2.json"),
                "stylish");
        assertEquals(readExpected("expected_stylish.txt"), result);
    }

    @Test
    void testGeneratePlainFromYaml() throws Exception {
        String result = Differ.generate(
                getYamlFilePath("file1.yaml"),
                getYamlFilePath("file2.yaml"),
                "plain");
        assertEquals(readExpected("expected_plain.txt"), result);
    }

    @Test
    void testGeneratePlainFromJson() throws Exception {
        String result = Differ.generate(
                getJsonFilePath("file1.json"),
                getJsonFilePath("file2.json"),
                "plain");
        assertEquals(readExpected("expected_plain.txt"), result);
    }

    @Test
    void testGenerateStylishByDefaultFromYaml() throws Exception {
        String result = Differ.generate(
                getYamlFilePath("file1.yaml"),
                getYamlFilePath("file2.yaml"));
        assertEquals(readExpected("expected_stylish.txt"), result);
    }

    @Test
    void testGenerateStylishByDefaultFromJson() throws Exception {
        String result = Differ.generate(
                getJsonFilePath("file1.json"),
                getJsonFilePath("file2.json"));
        assertEquals(readExpected("expected_stylish.txt"), result);
    }

    @Test
    void testGenerateInvalidFormat() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> Differ.generate(
                        getJsonFilePath("file1.json"),
                        getJsonFilePath("file2.json"),
                        "xml"));
        assertTrue(ex.getMessage().contains("Unknown format: xml"));
    }
}
