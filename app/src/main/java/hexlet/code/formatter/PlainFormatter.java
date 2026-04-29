package hexlet.code.formatter;

import hexlet.code.diff.DiffNode;

import java.util.List;
import java.util.stream.Collectors;

public class PlainFormatter {
    public static String format(List<DiffNode> tree) {
        String body = tree.stream()
                .map(PlainFormatter::formatNode)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining("\n"));

        return body + "\n";
    }

    private static String formatNode(DiffNode node) {
        return switch (node.getType()) {
            case ADDED -> String.format(
                    "Property '%s' was added with value: %s",
                    node.getKey(),
                    stringify(node.getNewValue())
            );

            case REMOVED -> String.format(
                    "Property '%s' was removed",
                    node.getKey()
            );

            case UNCHANGED -> "";

            case UPDATED -> String.format(
                    "Property '%s' was updated. From %s to %s",
                    node.getKey(),
                    stringify(node.getOldValue()),
                    stringify(node.getNewValue())
            );
        };
    }

    private static String stringify(Object value) {
        if (value == null) {
            return "null";
        }

        if (value instanceof String) {
            return "'" + value + "'";
        }

        if (value instanceof List || value instanceof java.util.Map) {
            return "[complex value]";
        }

        return value.toString();
    }
}
