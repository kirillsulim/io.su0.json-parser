package io.su0.json.path;

public class JsonPathFieldSegment implements JsonPathSegment {

    public static final JsonPathFieldSegment EMPTY = new JsonPathFieldSegment(null);

    private final String fieldName;

    public JsonPathFieldSegment(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
