package io.su0.json.path.parsing;

import io.su0.json.path.JsonPath;

public class Facade {

    public static JsonPath parse(String expr) {
        return Parser.parse(new Lexer(expr));
    }
}
