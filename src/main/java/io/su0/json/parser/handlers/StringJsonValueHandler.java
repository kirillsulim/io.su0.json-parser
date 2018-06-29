package io.su0.json.parser.handlers;

import io.su0.json.path.JsonPath;

public interface StringJsonValueHandler extends JsonValueHandler {

    void handle(JsonPath path, String value);
}
