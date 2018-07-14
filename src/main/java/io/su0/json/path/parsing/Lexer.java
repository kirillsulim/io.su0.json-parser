package io.su0.json.path.parsing;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Lexer implements Iterable<Token> {

    private static class LexerIterator implements Iterator<Token> {

        private static final Pattern LOOP_PATTERN;
        static {
            String loopPattern = Arrays.stream(TokenType.values())
                    .map(tokenType -> String.format("(?<%s>%s)", tokenType.groupName, tokenType.pattern))
                    .collect(Collectors.joining("|"));

            LOOP_PATTERN = Pattern.compile(loopPattern);
        }

        private final Matcher matcher;
        private final String pattern;

        private int lastEnd = 0;

        public LexerIterator(String pattern) {
            this.matcher = LOOP_PATTERN.matcher(pattern);
            this.pattern = pattern;
        }

        @Override
        public boolean hasNext() {
            return lastEnd != pattern.length();
        }

        @Override
        public Token next() {
            if (!matcher.find()) {
                throwIllegalSequence(pattern.length());
            }

            int start = matcher.start();
            if (start != lastEnd) {
                throwIllegalSequence(start);
            }
            lastEnd = matcher.end();

            for (TokenType token : TokenType.values()) {
                String group = matcher.group(token.groupName);
                if (Objects.nonNull(group)) {
                    return new Token(token, token.hasValue ? group : null);
                }
            }
            throw new IllegalStateException(String.format("No token matched at %d", lastEnd));
        }

        private void throwIllegalSequence(int start) {
            throw new IllegalStateException(String.format(
                    "Cannot parse sequence \"%s\" between (%d, %d)",
                    pattern.substring(lastEnd, start),
                    lastEnd,
                    start
            ));
        }
    }

    private final String string;

    public Lexer(String string) {
        this.string = string;
    }

    @Override
    public Iterator<Token> iterator() {
        return new LexerIterator(string);
    }
}
