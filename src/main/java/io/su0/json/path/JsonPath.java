package io.su0.json.path;

import java.util.ArrayDeque;
import java.util.Deque;

public class JsonPath {

    private final Deque<JsonPathSegment> segments = new ArrayDeque<>();

    public JsonPath() {
    }

    public Deque<JsonPathSegment> getSegments() {
        return segments;
    }
}
