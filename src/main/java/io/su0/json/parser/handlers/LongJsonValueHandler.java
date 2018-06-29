package io.su0.json.parser.handlers;

import io.su0.json.path.JsonPath;

public interface LongJsonValueHandler extends JsonValueHandler {

    void handle(JsonPath path, long value);
}
