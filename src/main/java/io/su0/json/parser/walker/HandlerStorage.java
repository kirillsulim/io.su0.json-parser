package io.su0.json.parser.walker;

import com.fasterxml.jackson.core.JsonToken;
import io.su0.json.parser.handlers.JsonValueHandler;
import io.su0.json.parser.handlers.JsonNodeValueHandler;
import io.su0.json.path.JsonPath;
import io.su0.json.path.matcher.JsonPathMatcher;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class HandlerStorage<Meta> {

    private static class Entry<Meta> {
        JsonPathMatcher matcher;
        JsonValueHandler<Meta> handler;

        Entry(JsonPathMatcher matcher, JsonValueHandler<Meta> handler) {
            this.matcher = matcher;
            this.handler = handler;
        }
    }

    private Collection<Entry<Meta>> handlers = new LinkedList<>();

    public void addHandler(JsonPathMatcher matcher, JsonValueHandler<Meta> handler) {
        handlers.add(new Entry<>(matcher, handler));
    }

    public Collection<JsonValueHandler<Meta>> getHandlers(JsonPath path, JsonToken jsonToken) {
        return handlers.stream()
                .filter(entry -> entry.matcher.match(path) && !(entry.handler instanceof JsonNodeValueHandler))
                .map(entry -> entry.handler)
                .collect(Collectors.toList());
    }

    public Collection<JsonNodeValueHandler<Meta>> getJsonNodeHandlers(JsonPath path, JsonToken jsonToken) {
        return handlers.stream()
                .filter(entry -> entry.matcher.match(path) && entry.handler instanceof JsonNodeValueHandler)
                .map(entry -> (JsonNodeValueHandler<Meta>) entry.handler)
                .collect(Collectors.toList());
    }
}
