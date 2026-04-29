package hexlet.code.formatter;

import hexlet.code.diff.DiffNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

import java.util.List;

public class JsonFormatter {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String format(List<DiffNode> tree) throws Exception {
        ObjectNode root = MAPPER.createObjectNode();

        for (DiffNode node : tree) {
            root.set(node.getKey(), formatNode(node));
        }

        return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(root);
    }

    private static ObjectNode formatNode(DiffNode node) {
        ObjectNode obj = MAPPER.createObjectNode();

        switch (node.getType()) {
            case ADDED -> {
                obj.put("status", "added");
                putValue(obj, "value", node.getNewValue());
            }
            case REMOVED -> {
                obj.put("status", "removed");
                putValue(obj, "value", node.getOldValue());
            }
            case UNCHANGED -> {
                obj.put("status", "unchanged");
                putValue(obj, "value", node.getOldValue());
            }
            case UPDATED -> {
                obj.put("status", "updated");
                putValue(obj, "oldValue", node.getOldValue());
                putValue(obj, "newValue", node.getNewValue());
            }
        }

        return obj;
    }

    private static void putValue(ObjectNode obj, String key, Object value) {
        if (value == null) {
            obj.putNull(key);
        } else if (value instanceof String s) {
            obj.put(key, s);
        } else if (value instanceof Boolean b) {
            obj.put(key, b);
        } else if (value instanceof Integer i) {
            obj.put(key, i);
        } else if (value instanceof java.util.Map || value instanceof java.util.List) {
            obj.put(key, "[complex value]");
        } else {
            obj.put(key, value.toString());
        }
    }
}
