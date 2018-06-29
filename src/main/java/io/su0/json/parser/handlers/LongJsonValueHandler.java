package io.su0.json.parser.handlers;

import io.su0.json.path.JsonPath;

public interface LongJsonValueHandler<Meta> extends JsonValueHandler<Meta> {

    void handle(JsonPath path, long value, Meta meta);
}
