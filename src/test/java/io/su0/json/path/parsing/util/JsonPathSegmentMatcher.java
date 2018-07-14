package io.su0.json.path.parsing.util;

import io.su0.json.path.matcher.JsonPathArraySegmentAnyMatcher;
import io.su0.json.path.matcher.JsonPathArraySegmentMatcher;
import io.su0.json.path.matcher.JsonPathFieldSegmentMatcher;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Objects;

public class JsonPathSegmentMatcher extends TypeSafeMatcher<io.su0.json.path.matcher.JsonPathSegmentMatcher> {

    private io.su0.json.path.matcher.JsonPathSegmentMatcher segment;

    public JsonPathSegmentMatcher(io.su0.json.path.matcher.JsonPathSegmentMatcher segment) {
        this.segment = segment;
    }

    @Override
    protected boolean matchesSafely(io.su0.json.path.matcher.JsonPathSegmentMatcher item) {
        if (segment instanceof JsonPathFieldSegmentMatcher) {
            return item instanceof JsonPathFieldSegmentMatcher &&
                    Objects.equals(
                            ((JsonPathFieldSegmentMatcher) segment).getName(),
                            ((JsonPathFieldSegmentMatcher) item).getName()
                    );
        } else if (segment instanceof JsonPathArraySegmentMatcher) {
            return item instanceof JsonPathArraySegmentMatcher &&
                    Objects.equals(
                            ((JsonPathArraySegmentMatcher) segment).getIndex(),
                            ((JsonPathArraySegmentMatcher) item).getIndex()
                    );
        } else if (segment instanceof JsonPathArraySegmentAnyMatcher) {
            return item instanceof JsonPathArraySegmentAnyMatcher;
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
        return new JsonPathSegmentMatcher(new JsonPathFieldSegmentMatcher(name));
    }

    public static JsonPathSegmentMatcher index(int index) {
        return new JsonPathSegmentMatcher(new JsonPathArraySegmentMatcher(index));
    }

    public static JsonPathSegmentMatcher arrayWildcard() {
        return new JsonPathSegmentMatcher(JsonPathArraySegmentAnyMatcher.INSTANCE);
    }
}
