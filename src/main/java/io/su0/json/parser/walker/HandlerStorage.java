package io.su0.json.parser.walker;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import io.su0.json.path.JsonPath;

import java.util.Collection;
import java.util.function.Consumer;

public interface HandlerStorage {

    Collection<Runnable> getHandlers(JsonPath path, JsonToken token);

    Collection<Consumer<JsonNode>> getValueHandlers(JsonPath path, JsonToken token);

    Collection<Runnable> getStartValueHandler(JsonPath path, JsonToken token);

    Collection<Runnable> getEndValueHandler(JsonPath path, JsonToken token);
}
