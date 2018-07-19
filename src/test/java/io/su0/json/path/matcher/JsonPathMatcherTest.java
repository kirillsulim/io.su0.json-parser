package io.su0.json.path.matcher;

import io.su0.json.path.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class JsonPathMatcherTest {

    @Test
    public void shouldMatchField() {
        JsonPathMatcher matcher = new JsonPathMatcher(Arrays.asList(
                new JsonPathFieldSegmentMatcher("abc")
        ));

        JsonPath path = new JsonPath();
        path.enterObject();
        path.enterField("abc");

        assertTrue(matcher.match(path));
    }

    @Test
    public void shouldMatchArrayIndex() {
        JsonPathMatcher matcher = new JsonPathMatcher(Arrays.asList(
                new JsonPathArraySegmentMatcher(1)
        ));

        JsonPath path = new JsonPath();
        path.enterArray();
        path.nextArrayElement();
        path.nextArrayElement();

        assertTrue(matcher.match(path));
    }

    @Test
    public void shouldMatchAnyArrayIndex() throws Exception {
        JsonPathMatcher matcher = new JsonPathMatcher(Arrays.asList(
                JsonPathArraySegmentAnyMatcher.INSTANCE
        ));

        JsonPath path = new JsonPath();
        path.enterArray();
        path.nextArrayElement();

        assertTrue(matcher.match(path));

        path.nextArrayElement();
        assertTrue(matcher.match(path));
    }

    @Test
    public void shouldMatchComplexStructure() {
        JsonPathMatcher matcher = new JsonPathMatcher(Arrays.asList(
                new JsonPathFieldSegmentMatcher("abc"),
                new JsonPathFieldSegmentMatcher("def"),
                new JsonPathArraySegmentMatcher(1),
                new JsonPathFieldSegmentMatcher("jkl"),
                new JsonPathArraySegmentMatcher(0),
                new JsonPathArraySegmentMatcher(2)
        ));

        JsonPath path = new JsonPath();
        path.enterObject();
        path.enterField("abc");
        path.enterObject();
        path.enterField("def");
        path.enterArray();
        path.nextArrayElement();
        path.nextArrayElement();
        path.enterObject();
        path.enterField("jkl");
        path.enterArray();
        path.nextArrayElement();
        path.enterArray();
        path.nextArrayElement();
        path.nextArrayElement();
        path.nextArrayElement();

        assertTrue(matcher.match(path));
    }
}

