package io.su0.json.parser.handlers;

import io.su0.json.path.JsonPath;

public interface BooleanJsonValueHandler<Meta> extends JsonValueHandler<Meta> {

    void handle(JsonPath path, boolean value, Meta meta);
}
