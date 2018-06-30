package io.su0.json.parser.walker;

import com.fasterxml.jackson.core.JsonToken;
import io.su0.json.parser.handlers.BlaHandler;
import io.su0.json.path.JsonPath;

import java.util.Collection;
import java.util.LinkedList;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public class HandlerStorage<Meta> {

    private static class Entry<Meta> {
        private final BiPredicate<JsonPath, JsonToken> predicate;
        private final BlaHandler<Meta> handler;

        public Entry(BiPredicate<JsonPath, JsonToken> predicate, BlaHandler<Meta> handler) {
            this.predicate = predicate;
            this.handler = handler;
        }
    }

    private Collection<Entry<Meta>> handlers = new LinkedList<>();

    public void addHandler(BiPredicate<JsonPath, JsonToken> predicate, BlaHandler<Meta> handler) {
        handlers.add(new Entry<>(predicate, handler));
    }

    public Collection<BlaHandler<Meta>> getHandlers(JsonPath path, JsonToken jsonToken) {
        return handlers.stream()
                .filter(entry -> entry.predicate.test(path, jsonToken))
                .map(entry -> entry.handler)
                .collect(Collectors.toList());
    }
}
