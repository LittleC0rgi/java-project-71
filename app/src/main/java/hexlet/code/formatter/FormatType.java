package hexlet.code.formatter;

public enum FormatType {
    STYLISH,
    PLAIN;

    public static FormatType from(String value) {
        return switch (value.toLowerCase()) {
            case "stylish" -> STYLISH;
            case "plain" -> PLAIN;
            default -> throw new IllegalArgumentException("Unknown format: " + value);
        };
    }
}
