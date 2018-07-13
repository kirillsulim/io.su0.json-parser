package io.su0.json.parser.walker;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import io.su0.json.path.JsonPath;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface HandlerStorage {

    Collection<Consumer<Context>> getHandlers(JsonPath path, JsonToken token);

    Collection<BiConsumer<Context, JsonNode>> getValueHandlers(JsonPath path, JsonToken token);

    Collection<Consumer<Context>> getStartValueHandlers(JsonPath path, JsonToken token);

    Collection<Consumer<Context>> getEndValueHandlers(JsonPath path, JsonToken token);
}
