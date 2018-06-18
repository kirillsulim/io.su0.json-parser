package io.su0.json.path.parsing;

import io.su0.json.path.JsonPath;
import io.su0.json.path.parsing.util.JsonPathSegmentMatcher;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

import static io.su0.json.path.parsing.util.JsonPathSegmentMatcher.index;
import static io.su0.json.path.parsing.util.JsonPathSegmentMatcher.object;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.*;

public class ParserTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowOnEmptySequence() {
        Parser.parse(Arrays.asList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIfSequenceNotStartWithRoot() {
        Parser.parse(Arrays.asList(Token.DOT));
    }

    @Test
    public void shouldParseRootSegment() {
        JsonPath result = Parser.parse(Arrays.asList(Token.ROOT));

        assertThat(result.getSegments(), hasItems());
    }

    @Test
    public void shouldParseSimpleSegment() {
        JsonPath result = Parser.parse(Arrays.asList(
                Token.ROOT,
                Token.DOT,
                new Token(TokenType.OBJECT_SEGMENT, "abc")
        ));

        assertThat(result.getSegments(), hasItems(object("abc")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowOnArrayStartAfterDot() {
        Parser.parse(Arrays.asList(
                Token.ROOT,
                Token.DOT,
                Token.ARRAY_START
        ));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowOnDotAfterArrayStart() {
        Parser.parse(Arrays.asList(
                Token.ROOT,
                Token.ARRAY_START,
                Token.DOT,
                new Token(TokenType.OBJECT_SEGMENT, "abc")
        ));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowOnDotAfterArrayIndex() {
        Parser.parse(Arrays.asList(
                Token.ROOT,
                Token.ARRAY_START,
                new Token(TokenType.ARRAY_INDEX, "123"),
                Token.DOT
        ));
    }

    @Test
    public void shouldParseSimpleArrayIndex() {
        JsonPath result = Parser.parse(Arrays.asList(
                Token.ROOT,
                Token.ARRAY_START,
                new Token(TokenType.ARRAY_INDEX, "123"),
                Token.ARRAY_END
        ));

        assertThat(result.getSegments(), hasItems(index(123)));
    }
}