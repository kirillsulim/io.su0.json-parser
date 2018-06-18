package io.su0.json.path.parsing.util;

import io.su0.json.path.JsonPathArraySegment;
import io.su0.json.path.JsonPathObjectSegment;
import io.su0.json.path.JsonPathSegment;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Objects;

public class JsonPathSegmentMatcher extends TypeSafeMatcher<JsonPathSegment> {

    private JsonPathSegment segment;

    public JsonPathSegmentMatcher(JsonPathSegment segment) {
        this.segment = segment;
    }

    @Override
    protected boolean matchesSafely(JsonPathSegment item) {
        if (segment instanceof JsonPathObjectSegment) {
            return item instanceof JsonPathObjectSegment &&
                    Objects.equals(
                            ((JsonPathObjectSegment) segment).getName(),
                            ((JsonPathObjectSegment) item).getName()
                    );
        }
        else if (segment instanceof JsonPathArraySegment) {
            return item instanceof JsonPathArraySegment &&
                    Objects.equals(
                            ((JsonPathArraySegment) segment).getIndex(),
                            ((JsonPathArraySegment) item).getIndex()
                    );
        }
        throw new IllegalArgumentException();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("segment of type ")
                .appendText(segment.getClass().getSimpleName())
                .appendText(" ")
                .appendValue(segment);
    }

    public static JsonPathSegmentMatcher object(String name) {
        return new JsonPathSegmentMatcher(new JsonPathObjectSegment(name));
    }

    public static JsonPathSegmentMatcher index(int index) {
        return new JsonPathSegmentMatcher(new JsonPathArraySegment(index));
    }
}
