package io.su0.json.path.matcher;

import io.su0.json.path.JsonPath;
import io.su0.json.path.JsonPathSegment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

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

    public JsonPathMatcher append(JsonPathMatcher matcher) {
        List<JsonPathSegmentMatcher> newSeg = new ArrayList<>(this.segments.size() + matcher.segments.size());
        newSeg.addAll(this.segments);
        newSeg.addAll(matcher.segments);
        return new JsonPathMatcher(newSeg);
    }

    @Override
    public String toString() {
        return "$" + segments.stream().map(Object::toString).collect(Collectors.joining());
    }
}
