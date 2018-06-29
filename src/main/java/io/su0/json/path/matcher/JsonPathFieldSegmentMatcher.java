package io.su0.json.path.matcher;

import io.su0.json.path.JsonPathFieldSegment;
import io.su0.json.path.JsonPathSegment;

import java.util.Objects;

public class JsonPathFieldSegmentMatcher implements JsonPathSegmentMatcher {

    private final String name;

    public JsonPathFieldSegmentMatcher(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean match(JsonPathSegment segment) {
        return segment instanceof JsonPathFieldSegment && Objects.equals(((JsonPathFieldSegment) segment).getFieldName(), name);
    }
}
