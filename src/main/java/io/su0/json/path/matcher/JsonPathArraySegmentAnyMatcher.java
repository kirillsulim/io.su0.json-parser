package io.su0.json.path.matcher;

import io.su0.json.path.JsonPathArraySegment;
import io.su0.json.path.JsonPathSegment;

public class JsonPathArraySegmentAnyMatcher implements JsonPathSegmentMatcher {

    public static final JsonPathArraySegmentAnyMatcher INSTANCE = new JsonPathArraySegmentAnyMatcher();

    private JsonPathArraySegmentAnyMatcher() {
    }

    @Override
    public boolean match(JsonPathSegment segment) {
        return segment instanceof JsonPathArraySegment;
    }

    @Override
    public String toString() {
        return "[*]";
    }
}
