package io.su0.json.path.matcher;

import io.su0.json.path.JsonPath;
import io.su0.json.path.JsonPathSegment;

import java.util.Iterator;
import java.util.List;

public class JsonPathMatcher {

    private final List<JsonPathSegmentMatcher> segments;

    public JsonPathMatcher(List<JsonPathSegmentMatcher> segments) {
        this.segments = segments;
    }

    public boolean match(JsonPath path) {
        Iterator<JsonPathSegment> pathSegments = path.getSegments().iterator();
        Iterator<JsonPathSegmentMatcher> matchers = segments.iterator();

        while (pathSegments.hasNext() && matchers.hasNext()) {
            if (!matchers.next().match(pathSegments.next())) {
                return false;
            }
        }

        return !(pathSegments.hasNext() || matchers.hasNext());
    }

    public List<JsonPathSegmentMatcher> getSegments() {
        return segments;
    }
}
