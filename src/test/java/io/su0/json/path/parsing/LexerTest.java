package io.su0.json.path.parsing;

import org.junit.Test;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static io.su0.json.path.parsing.util.TokenMatcher.*;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class LexerTest {

    @Test
    public void shouldLexEmptyString() {
        Collection<Token> tokens = lexToCollection("");

        assertEquals(0, tokens.size());
    }


    @Test
    public void shouldLexRootToken() {
        Collection<Token> tokens = lexToCollection("$");

        assertThat(tokens, hasItems(root()));
    }

    @Test
    public void shouldLexSimplePath() {
        Collection<Token> tokens = lexToCollection("$.abc");

        assertThat(tokens, hasItems(
                root(),
                dot(),
                token(new Token(TokenType.OBJECT_SEGMENT, "abc"))
        ));
    }

    @Test
    public void shouldLexSimpleArray() {
        Collection<Token> tokens = lexToCollection("$[123]");

        assertThat(tokens, hasItems(
                root(),
                arrayStart(),
                token(new Token(TokenType.ARRAY_INDEX, "123")),
                arrayEnd()
        ));
    }

    @Test
    public void shouldLexComplexExpression() {
        Collection<Token> tokens = lexToCollection("$.abc[123].def");

        assertThat(tokens, hasItems(
                root(),
                dot(),
                token(new Token(TokenType.OBJECT_SEGMENT, "abc")),
                arrayStart(),
                token(new Token(TokenType.ARRAY_INDEX, "123")),
                arrayEnd(),
                dot(),
                token(new Token(TokenType.OBJECT_SEGMENT, "def"))
        ));
    }

    @Test
    public void shouldLexWildcardArray() throws Exception {
        Collection<Token> tokens = lexToCollection("$[*]");

        assertThat(tokens, hasItems(
                root(),
                arrayStart(),
                wildcard(),
                arrayEnd()
        ));
    }

    private static Collection<Token> lexToCollection(String expression) {
        return StreamSupport.stream(new Lexer(expression).spliterator(), false).collect(Collectors.toList());
    }
}
