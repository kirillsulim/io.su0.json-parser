package io.su0.json.path.parsing;

import io.su0.json.path.JsonPath;
import org.junit.Test;

import static io.su0.json.path.parsing.util.JsonPathSegmentMatcher.index;
import static io.su0.json.path.parsing.util.JsonPathSegmentMatcher.object;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

public class FacadeTest {

    @Test
    public void shouldParseRoot() {
        JsonPath result = Facade.parse("$");

        assertThat(result.getSegments(), hasItems());
    }

    @Test
    public void shouldParseSimpleSegment() {
        JsonPath result = Facade.parse("$.abc");

        assertThat(result.getSegments(), hasItems(
                object("abc")
        ));
    }

    @Test
    public void shouldParseSimpleArrayIndex() {
        JsonPath result = Facade.parse("$[123]");

        assertThat(result.getSegments(), hasItems(
                index(123)
        ));
    }

    @Test
    public void shouldParseComplexStructure() {
        JsonPath result = Facade.parse("$.abc[123].def[456][789].jkl.xyz");

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
