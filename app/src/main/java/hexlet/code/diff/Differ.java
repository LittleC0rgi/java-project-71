package hexlet.code.diff;

import hexlet.code.formatter.FormatType;
import hexlet.code.formatter.Formatter;
import hexlet.code.formatter.StylishFormatter;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class Differ {
    private static List<DiffNode> buildTree(Map<String, Object> data1,
                                            Map<String, Object> data2) {

        return Stream.concat(data1.keySet().stream(), data2.keySet().stream())
                .distinct()
                .sorted()
                .map(key -> buildNode(key, data1, data2))
                .flatMap(List::stream)
                .toList();
    }

    private static List<DiffNode> buildNode(String key,
                                            Map<String, Object> d1,
                                            Map<String, Object> d2) {
        boolean in1 = d1.containsKey(key);
        boolean in2 = d2.containsKey(key);

        if (in1 && !in2) {
            return List.of(new DiffNode(key, DiffType.REMOVED, d1.get(key), null));
        }

        if (!in1 && in2) {
            return List.of(new DiffNode(key, DiffType.ADDED, null, d2.get(key)));
        }

        Object v1 = d1.get(key);
        Object v2 = d2.get(key);

        if (Objects.equals(v1, v2)) {
            return List.of(new DiffNode(key, DiffType.UNCHANGED, v1, null));
        }

        return List.of(new DiffNode(key, DiffType.UPDATED, v1, v2));
    }

    public static String generate(Map<String, Object> d1,
                                  Map<String, Object> d2,
                                  FormatType format) throws Exception {

        var tree = buildTree(d1, d2);
        return Formatter.format(format, tree);
    }

    public static String generate(Map<String, Object> d1,
                                  Map<String, Object> d2) {

        var tree = buildTree(d1, d2);
        return StylishFormatter.format(tree);
    }
}
