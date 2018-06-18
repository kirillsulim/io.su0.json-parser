package io.su0.json.path.parsing;

public class Token {

    public static final Token ROOT = new Token(TokenType.ROOT, null);
    public static final Token DOT = new Token(TokenType.DOT, null);
    public static final Token ARRAY_START = new Token(TokenType.ARRAY_START, null);
    public static final Token ARRAY_END = new Token(TokenType.ARRAY_END, null);

    private final TokenType type;
    private final String value;

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
