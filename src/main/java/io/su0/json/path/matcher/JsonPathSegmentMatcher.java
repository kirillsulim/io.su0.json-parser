package io.su0.json.path.matcher;

import io.su0.json.path.JsonPathSegment;

public interface JsonPathSegmentMatcher {
    boolean match(JsonPathSegment segment);
}
