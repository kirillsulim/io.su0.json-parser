package io.su0.json.parser.walker;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import io.su0.json.parser.Ref;
import io.su0.json.path.JsonPath;
import io.su0.json.path.matcher.JsonPathMatcher;

import java.util.Collection;
import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class HandlerStorageImpl<Context> {

    private static class Entry<Context> {
        private final JsonPathMatcher matcher;
        private final Consumer<Context> handler;

        public Entry(JsonPathMatcher matcher, Consumer<Context> handler) {
            this.matcher = matcher;
            this.handler = handler;
        }
    }

    private static class ValueConsumerEntry<Context> {
        private final JsonPathMatcher matcher;
        private final BiConsumer<Context, JsonNode> handler;

        public ValueConsumerEntry(JsonPathMatcher matcher, BiConsumer<Context, JsonNode> handler) {
            this.matcher = matcher;
            this.handler = handler;
        }
    }

    private final Collection<ValueConsumerEntry<Context>> valueHandlers = new LinkedList<>();

    private final Collection<Entry<Context>> startObjectHandlers = new LinkedList<>();
    private final Collection<Entry<Context>> endObjectHandlers = new LinkedList<>();
    private final Collection<Entry<Context>> startArrayHandlers = new LinkedList<>();
    private final Collection<Entry<Context>> endArrayHandlers = new LinkedList<>();


    public void addValueHandler(JsonPathMatcher matcher, BiConsumer<Context, JsonNode> consumer) {
        valueHandlers.add(new ValueConsumerEntry<>(matcher, consumer));
    }

    public void addStartObjectHandler(JsonPathMatcher matcher, Consumer<Context> runnable) {
        startObjectHandlers.add(new Entry<>(matcher, runnable));
    }

    public void addEndObjectHandler(JsonPathMatcher matcher, Consumer<Context> runnable) {
        endObjectHandlers.add(new Entry<>(matcher, runnable));
    }

    public void addStartArrayHandler(JsonPathMatcher matcher, Consumer<Context> runnable) {
        startArrayHandlers.add(new Entry<>(matcher, runnable));
    }

    public void addEndArrayHandler(JsonPathMatcher matcher, Consumer<Context> runnable) {
        endArrayHandlers.add(new Entry<>(matcher, runnable));
    }

    public <InnerContext> void append(JsonPathMatcher matcher, HandlerStorageImpl<InnerContext> innerStorage, Ref<InnerContext> ref) {
        for (ValueConsumerEntry<InnerContext> entry : innerStorage.valueHandlers) {
            valueHandlers.add(new ValueConsumerEntry<>(matcher.append(entry.matcher), (context, jsonNode) -> {
                entry.handler.accept(ref.getRef(), jsonNode);
            }));
        }
    }

    public Collection<Consumer<Context>> getHandlers(JsonPath path, JsonToken token) {
        if (JsonToken.START_OBJECT == token) {
            return startObjectHandlers.stream().filter(entry -> entry.matcher.match(path)).map(entry -> entry.handler).collect(Collectors.toSet());
        } else if (JsonToken.END_OBJECT == token) {
            return endObjectHandlers.stream().filter(entry -> entry.matcher.match(path)).map(entry -> entry.handler).collect(Collectors.toSet());
        } else if (JsonToken.START_ARRAY == token) {
            return startArrayHandlers.stream().filter(entry -> entry.matcher.match(path)).map(entry -> entry.handler).collect(Collectors.toSet());
        } else if (JsonToken.END_ARRAY == token) {
            return endArrayHandlers.stream().filter(entry -> entry.matcher.match(path)).map(entry -> entry.handler).collect(Collectors.toSet());
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Collection<BiConsumer<Context, JsonNode>> getValueHandlers(JsonPath path, JsonToken token) {
        return valueHandlers.stream().filter(e -> e.matcher.match(path)).map(e -> e.handler).collect(Collectors.toList());
    }
}
