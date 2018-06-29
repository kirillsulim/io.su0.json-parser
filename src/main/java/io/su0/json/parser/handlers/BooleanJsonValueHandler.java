package io.su0.json.parser.handlers;

import io.su0.json.path.JsonPath;

public interface BooleanJsonValueHandler extends JsonValueHandler {

    void handle(JsonPath path, boolean value);
}
