package hexlet.code;

import hexlet.code.formatter.FormatType;
import hexlet.code.formatter.Formatter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Differ {
    public static String generate(String filePath1, String filePath2, String format) throws Exception {
        Path firstFile = Path.of(filePath1);
        String firstFileContent = readFile(firstFile);
        String firstFileFormat = getFormat(firstFile);

        Path secondFile = Path.of(filePath2);
        String secondFileContent = readFile(secondFile);
        String secondFileFormat = getFormat(secondFile);

        Map<String, Object> d1 = Parser.parse(firstFileContent, firstFileFormat);
        Map<String, Object> d2 = Parser.parse(secondFileContent, secondFileFormat);
        var type = FormatType.from(format);
        var tree = DiffCalculator.calculateDiff(d1, d2);
        return Formatter.format(type, tree);
    }

    public static String generate(String filePath1, String filePath2) throws Exception {
        return generate(filePath1, filePath2, FormatType.STYLISH.toString());
    }

    private static String readFile(Path path) throws Exception {
        return Files.readString(path);
    }

    private static String getFormat(Path path) {
        String fileName = path.getFileName().toString();
        int dotIndex = fileName.lastIndexOf(".");
        return dotIndex == -1 ? "" : fileName.substring(dotIndex + 1);
    }
}
