package hexlet.code.formatter;

import hexlet.code.diff.DiffNode;

import java.util.List;

public class Formatter {
    public static String format(FormatType type, List<DiffNode> tree) {
        return switch (type) {
            case STYLISH -> StylishFormatter.format(tree);
            case PLAIN -> PlainFormatter.format(tree);
        };
    }
}
