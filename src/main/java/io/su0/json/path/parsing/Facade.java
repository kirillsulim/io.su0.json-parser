package io.su0.json.path.parsing;

import io.su0.json.path.matcher.JsonPathMatcher;

public class Facade {

    public static JsonPathMatcher parse(String expr) {
        return Parser.parse(new Lexer(expr));
    }
}
