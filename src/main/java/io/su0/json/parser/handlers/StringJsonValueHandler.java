package io.su0.json.parser.handlers;

import io.su0.json.path.JsonPath;

public interface StringJsonValueHandler<Meta> extends JsonValueHandler<Meta> {

    void handle(JsonPath path, String value, Meta meta);
}
