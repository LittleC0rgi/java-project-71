package hexlet.code.formatter;

public enum FormatType {
    STYLISH,
    JSON,
    PLAIN;

    public static FormatType from(String value) {
        return switch (value.toLowerCase()) {
            case "stylish" -> STYLISH;
            case "plain" -> PLAIN;
            case "json" -> JSON;
            default -> throw new IllegalArgumentException("Unknown format: " + value);
        };
    }
}
