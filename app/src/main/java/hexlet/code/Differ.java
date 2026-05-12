package hexlet.code;

import hexlet.code.formatter.FormatType;
import hexlet.code.formatter.Formatter;

import java.nio.file.Path;
import java.util.Map;

public class Differ {
    public static String generate(String filePath1, String filePath2, String format) throws Exception {
        Map<String, Object> d1 = Parser.getData(Path.of(filePath1));
        Map<String, Object> d2 = Parser.getData(Path.of(filePath2));
        var type = FormatType.from(format);
        var tree = DiffCalculator.calculateDiff(d1, d2);
        return Formatter.format(type, tree);
    }

    public static String generate(String filePath1, String filePath2) throws Exception {
        Map<String, Object> d1 = Parser.getData(Path.of(filePath1));
        Map<String, Object> d2 = Parser.getData(Path.of(filePath2));
        var tree = DiffCalculator.calculateDiff(d1, d2);
        return Formatter.format(FormatType.STYLISH, tree);
    }
}
