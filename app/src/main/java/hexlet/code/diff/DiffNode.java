package hexlet.code.diff;

public class DiffNode {
    private final String key;
    private final DiffType type;
    private final Object oldValue;
    private final Object newValue;

    public DiffNode(String key, DiffType type, Object oldValue, Object newValue) {
        this.key = key;
        this.type = type;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public String getKey() {
        return key;
    }

    public DiffType getType() {
        return type;
    }

    public Object getOldValue() {
        return oldValue;
    }

    public Object getNewValue() {
        return newValue;
    }
}
