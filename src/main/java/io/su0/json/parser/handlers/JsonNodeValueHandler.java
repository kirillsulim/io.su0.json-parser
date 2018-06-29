package io.su0.json.parser.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import io.su0.json.path.JsonPath;

public interface JsonNodeValueHandler<Meta> extends JsonValueHandler<Meta> {

    void handle(JsonPath path, JsonNode jsonNode, Meta meta);
}
