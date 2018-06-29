package io.su0.json.parser.handlers;

import io.su0.json.path.JsonPath;

public interface IntJsonValueHandler<Meta> extends JsonValueHandler<Meta> {

    void handle(JsonPath path, int value, Meta meta);
}
