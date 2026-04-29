package hexlet.code.formatter;

import hexlet.code.diff.DiffNode;

import java.util.List;
import java.util.stream.Collectors;

public class StylishFormatter {

    public static String format(List<DiffNode> tree) {
        String body = tree.stream()
                .map(StylishFormatter::formatNode)
                .collect(Collectors.joining("\n"));

        return "{\n" + body + "\n}";
    }

    private static String formatNode(DiffNode node) {
        return switch (node.getType()) {
            case ADDED -> "  + " + node.getKey() + ": " + node.getNewValue();
            case REMOVED -> "  - " + node.getKey() + ": " + node.getOldValue();
            case UNCHANGED -> "    " + node.getKey() + ": " + node.getOldValue();
            case UPDATED -> "  - " + node.getKey() + ": " + node.getOldValue() + "\n"
                    + "  + " + node.getKey() + ": " + node.getNewValue();
        };
    }
}
