package io.su0.json.path.parsing.util;

import io.su0.json.path.parsing.Token;
import io.su0.json.path.parsing.TokenType;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Objects;

public class TokenMatcher extends TypeSafeMatcher<Token> {

    private final Token token;

    private TokenMatcher(Token token) {
        this.token = token;
    }

    @Override
    protected boolean matchesSafely(Token item) {
        return Objects.equals(token.getType(), item.getType()) && Objects.equals(token.getValue(), item.getValue());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Tokens mismatch");
    }

    public static TokenMatcher token(Token token) {
        return new TokenMatcher(token);
    }

    public static TokenMatcher root() {
        return new TokenMatcher(new Token(TokenType.ROOT, null));
    }
}
