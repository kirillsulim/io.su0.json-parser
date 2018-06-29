package io.su0.json.parser.handlers;

import io.su0.json.path.JsonPath;

public interface DoubleJsonValueHandler<Meta> extends JsonValueHandler<Meta> {

    void handle(JsonPath path, double value, Meta meta);
}
