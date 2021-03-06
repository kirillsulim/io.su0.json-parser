package io.su0.json.path.parsing;

import io.su0.json.path.matcher.JsonPathMatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static io.su0.json.path.parsing.util.JsonPathSegmentMatcher.*;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;

public class ParserTest {

    @Test
    public void shouldThrowOnEmptySequence() {
        Assertions.assertThrows(
                IllegalStateException.class,
                () -> Parser.parse(Arrays.asList())
        );
    }

    @Test
    public void shouldThrowIfSequenceNotStartWithRoot() {
        Assertions.assertThrows(
                IllegalStateException.class,
                () -> Parser.parse(Arrays.asList(Token.DOT))
        );
    }

    @Test
    public void shouldParseRootSegment() {
        JsonPathMatcher result = Parser.parse(Arrays.asList(Token.ROOT));

        assertThat(result.getSegments(), hasItems());
    }

    @Test
    public void shouldParseSimpleSegment() {
        JsonPathMatcher result = Parser.parse(Arrays.asList(
                Token.ROOT,
                Token.DOT,
                new Token(TokenType.OBJECT_SEGMENT, "abc")
        ));

        assertThat(result.getSegments(), hasItems(object("abc")));
    }

    @Test
    public void shouldThrowOnArrayStartAfterDot() {
        Assertions.assertThrows(
                IllegalStateException.class,
                () -> Parser.parse(Arrays.asList(
                        Token.ROOT,
                        Token.DOT,
                        Token.ARRAY_START
                ))
        );
    }

    @Test
    public void shouldThrowOnDotAfterArrayStart() {
        Assertions.assertThrows(
                IllegalStateException.class,
                () -> Parser.parse(Arrays.asList(
                        Token.ROOT,
                        Token.ARRAY_START,
                        Token.DOT,
                        new Token(TokenType.OBJECT_SEGMENT, "abc")
                ))
        );
    }

    @Test
    public void shouldThrowOnDotAfterArrayIndex() {
        Assertions.assertThrows(
                IllegalStateException.class,
                () -> Parser.parse(Arrays.asList(
                        Token.ROOT,
                        Token.ARRAY_START,
                        new Token(TokenType.ARRAY_INDEX, "123"),
                        Token.DOT
                ))
        );
    }

    @Test
    public void shouldParseSimpleArrayIndex() {
        JsonPathMatcher result = Parser.parse(Arrays.asList(
                Token.ROOT,
                Token.ARRAY_START,
                new Token(TokenType.ARRAY_INDEX, "123"),
                Token.ARRAY_END
        ));

        assertThat(result.getSegments(), hasItems(index(123)));
    }

    @Test
    public void shouldParseSimpleArrayWildcard() throws Exception {
        JsonPathMatcher result = Parser.parse(Arrays.asList(
                Token.ROOT,
                Token.ARRAY_START,
                Token.WILDCARD,
                Token.ARRAY_END
        ));

        assertThat(result.getSegments(), hasItems(arrayWildcard()));
    }
}
