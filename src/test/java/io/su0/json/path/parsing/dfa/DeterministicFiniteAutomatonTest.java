package io.su0.json.path.parsing.dfa;

import io.su0.json.path.matcher.JsonPathSegmentMatcher;
import io.su0.json.path.parsing.Token;
import io.su0.json.path.parsing.TokenType;
import org.hamcrest.CoreMatchers;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;

import static io.su0.json.path.parsing.util.JsonPathSegmentMatcher.object;
import static org.hamcrest.CoreMatchers.hasItems;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class DeterministicFiniteAutomatonTest {

    private DeterministicFiniteAutomaton dfa;

    @Before
    public void setUp() throws Exception {
        dfa = new DeterministicFiniteAutomaton();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionForFirstNotRootToken() throws Exception {
        // TODO: add more
        dfa.accept(Token.ARRAY_END);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionIfNotInFinalState() throws Exception {
        dfa.accept(Token.ROOT);
        dfa.accept(Token.DOT);

        LinkedList<JsonPathSegmentMatcher> result = dfa.finish();
    }

    @Test
    public void shouldParseSimpleField() throws Exception {
        dfa.accept(Token.ROOT);
        dfa.accept(Token.DOT);
        dfa.accept(new Token(TokenType.OBJECT_SEGMENT, "abc"));

        assertThat(dfa.finish(), hasItems(
                object("abc")
        ));
    }
}
