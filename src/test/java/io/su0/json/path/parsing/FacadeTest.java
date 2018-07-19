package io.su0.json.path.parsing;

import io.su0.json.path.matcher.JsonPathMatcher;
import org.junit.jupiter.api.Test;

import static io.su0.json.path.parsing.util.JsonPathSegmentMatcher.index;
import static io.su0.json.path.parsing.util.JsonPathSegmentMatcher.object;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItems;

public class FacadeTest {

    @Test
    public void shouldParseRoot() {
        JsonPathMatcher result = Facade.parse("$");

        assertThat(result.getSegments(), hasItems());
    }

    @Test
    public void shouldParseSimpleSegment() {
        JsonPathMatcher result = Facade.parse("$.abc");

        assertThat(result.getSegments(), hasItems(
                object("abc")
        ));
    }

    @Test
    public void shouldParseSimpleArrayIndex() {
        JsonPathMatcher result = Facade.parse("$[123]");

        assertThat(result.getSegments(), hasItems(
                index(123)
        ));
    }

    @Test
    public void shouldParseComplexStructure() {
        JsonPathMatcher result = Facade.parse("$.abc[123].def[456][789].jkl.xyz");

        assertThat(result.getSegments(), hasItems(
                object("abc"),
                index(123),
                object("def"),
                index(456),
                index(789),
                object("jkl"),
                object("xyz")
        ));
    }
}
