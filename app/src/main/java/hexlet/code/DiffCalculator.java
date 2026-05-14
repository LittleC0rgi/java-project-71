package hexlet.code;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class DiffCalculator {
    public static List<DiffNode> calculateDiff(Map<String, Object> data1, Map<String, Object> data2) {
        return Stream.concat(data1.keySet().stream(), data2.keySet().stream())
                .distinct()
                .sorted()
                .map(key -> buildNode(key, data1, data2))
                .flatMap(List::stream)
                .toList();
    }

    private static List<DiffNode> buildNode(String key, Map<String, Object> d1, Map<String, Object> d2) {
        boolean in1 = d1.containsKey(key);
        boolean in2 = d2.containsKey(key);

        Object v1 = d1.get(key);
        Object v2 = d2.get(key);

        if (in1 && !in2) {
            return List.of(new DiffNode(key, DiffType.REMOVED, v1, null));
        } else if (!in1 && in2) {
            return List.of(new DiffNode(key, DiffType.ADDED, null, v2));
        } else if (Objects.equals(v1, v2)) {
            return List.of(new DiffNode(key, DiffType.UNCHANGED, v1, null));
        } else {
            return List.of(new DiffNode(key, DiffType.UPDATED, v1, v2));
        }
    }
}
