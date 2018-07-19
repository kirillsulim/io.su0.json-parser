package io.su0.json.path.parsing.dfa;

import io.su0.json.path.parsing.Token;
import io.su0.json.path.parsing.TokenType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.su0.json.path.parsing.util.JsonPathSegmentMatcher.object;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;

public class DeterministicFiniteAutomatonTest {

    private DeterministicFiniteAutomaton dfa;

    @BeforeEach
    public void setUp() {
        dfa = new DeterministicFiniteAutomaton();
    }

    // TODO: parametrized
    @Test
    public void shouldThrowExceptionForFirstNotRootToken() {
        Assertions.assertThrows(
                IllegalStateException.class,
                () -> dfa.accept(Token.ARRAY_END)
        );
    }

    @Test
    public void shouldThrowExceptionIfNotInFinalState() {
        Assertions.assertThrows(
                IllegalStateException.class,
                () -> {
                    dfa.accept(Token.ROOT);
                    dfa.accept(Token.DOT);
                    dfa.finish();
                }
        );
    }

    @Test
    public void shouldParseSimpleField() {
        dfa.accept(Token.ROOT);
        dfa.accept(Token.DOT);
        dfa.accept(new Token(TokenType.OBJECT_SEGMENT, "abc"));

        assertThat(dfa.finish(), hasItems(
                object("abc")
        ));
    }
}
