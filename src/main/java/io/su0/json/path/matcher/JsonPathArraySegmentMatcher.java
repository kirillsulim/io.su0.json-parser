package io.su0.json.path.matcher;

import io.su0.json.path.JsonPathArraySegment;
import io.su0.json.path.JsonPathSegment;

public class JsonPathArraySegmentMatcher implements JsonPathSegmentMatcher {

    private final int index;

    public JsonPathArraySegmentMatcher(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean match(JsonPathSegment segment) {
        return segment instanceof JsonPathArraySegment && ((JsonPathArraySegment) segment).getIndex() == index;
    }
}
