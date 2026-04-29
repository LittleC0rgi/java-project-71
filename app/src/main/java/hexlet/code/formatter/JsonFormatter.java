package hexlet.code.formatter;

import hexlet.code.diff.DiffNode;

import java.util.List;

public class JsonFormatter {

    public static String format(List<DiffNode> tree) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");

        for (int i = 0; i < tree.size(); i++) {
            DiffNode node = tree.get(i);

            sb.append("  \"")
                    .append(node.getKey())
                    .append("\": ")
                    .append(formatNode(node));

            if (i < tree.size() - 1) {
                sb.append(",");
            }

            sb.append("\n");
        }

        sb.append("}");
        return sb.toString();
    }

    private static String formatNode(DiffNode node) {
        return switch (node.getType()) {
            case ADDED -> "{\n"
                    + "    \"status\": \"added\",\n"
                    + "    \"value\": " + stringify(node.getNewValue()) + "\n"
                    + "  }";

            case REMOVED -> "{\n"
                    + "    \"status\": \"removed\",\n"
                    + "    \"value\": " + stringify(node.getOldValue()) + "\n"
                    + "  }";

            case UNCHANGED -> "{\n"
                    + "    \"status\": \"unchanged\",\n"
                    + "    \"value\": " + stringify(node.getOldValue()) + "\n"
                    + "  }";

            case UPDATED -> "{\n"
                    + "    \"status\": \"updated\",\n"
                    + "    \"oldValue\": " + stringify(node.getOldValue()) + ",\n"
                    + "    \"newValue\": " + stringify(node.getNewValue()) + "\n"
                    + "  }";
        };
    }

    private static String stringify(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof String) {
            return "\"" + value + "\"";
        }
        if (value instanceof java.util.Map || value instanceof java.util.List) {
            return "\"[complex value]\"";
        }
        return value.toString();
    }
}
