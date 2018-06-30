package io.su0.json.parser.handlers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import io.su0.json.path.JsonPath;

import java.io.IOException;

public interface BlaHandler<Meta> {
    void handle(JsonParser parser, JsonPath path, JsonToken token, Meta meta) throws IOException;
}
