package io.su0.json.parser.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import io.su0.json.path.JsonPath;

public interface JsonNodeHandler<Meta> {

    void handle(JsonPath path, JsonNode node, Meta meta);
}
