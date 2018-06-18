package io.su0.json.path;

import java.util.*;

public class JsonPath {

    private final List<JsonPathSegment> segments = new ArrayList<>();

    public JsonPath() {
    }

    public void add(JsonPathSegment segment) {
        segments.add(segment);
    }

    public void pop() {
        if (0 == segments.size()) {
            throw new IllegalStateException("Pop from empty path");
        }
        segments.remove(segments.size() - 1);
    }

    public List<JsonPathSegment> getSegments() {
        return Collections.unmodifiableList(segments);
    }
}
