package io.su0.json.parser.walker;

import com.fasterxml.jackson.core.JsonToken;
import io.su0.json.parser.handlers.JsonValueHandler;
import io.su0.json.parser.handlers.JsonNodeValueHandler;
import io.su0.json.path.JsonPath;
import io.su0.json.path.matcher.JsonPathMatcher;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class HandlerStorage {

    private static class Bla {
        JsonPathMatcher matcher;
        JsonValueHandler handler;

        public Bla(JsonPathMatcher matcher, JsonValueHandler handler) {
            this.matcher = matcher;
            this.handler = handler;
        }
    }

    Collection<Bla> handlers = new LinkedList<>();

    public void addHandler(JsonPathMatcher matcher, JsonValueHandler handler) {
        handlers.add(new Bla(matcher, handler));
    }

    public Collection<JsonValueHandler> getHandlers(JsonPath path, JsonToken jsonToken) {
        return handlers.stream()
                .filter(bla -> bla.matcher.match(path) && !(bla.handler instanceof JsonNodeValueHandler))
                .map(bla -> bla.handler)
                .collect(Collectors.toList());
    }

    public Collection<JsonNodeValueHandler> getJsonNodeHandlers(JsonPath path, JsonToken jsonToken) {
        return handlers.stream()
                .filter(bla -> bla.matcher.match(path) && bla.handler instanceof JsonNodeValueHandler)
                .map(bla -> (JsonNodeValueHandler) bla.handler)
                .collect(Collectors.toList());
    }
}
