package io.su0.json.path.parsing;

import io.su0.json.path.parsing.util.TokenMatcher;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static io.su0.json.path.parsing.util.TokenMatcher.root;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.*;

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

    private static Collection<Token> lexToCollection(String expression) {
        return StreamSupport.stream(new Lexer(expression).spliterator(), false).collect(Collectors.toList());
    }
}
