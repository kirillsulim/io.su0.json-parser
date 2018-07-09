package io.su0.json.parser.walker;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import io.su0.json.path.JsonPath;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class HandlerStorageWithContext<Context> implements HandlerStorage {

    private final HandlerStorageImpl<Context> handlerStorage;
    private final Context context;

    public HandlerStorageWithContext(HandlerStorageImpl<Context> handlerStorage, Context context) {
        this.handlerStorage = handlerStorage;
        this.context = context;
    }

    @Override
    public Collection<Runnable> getHandlers(JsonPath path, JsonToken token) {
        return handlerStorage.getHandlers(path, token).stream().map(consumer -> new Runnable() {
            @Override
            public void run() {
                consumer.accept(context);
            }
        } ).collect(Collectors.toList());

    }

    @Override
    public Collection<Consumer<JsonNode>> getValueHandlers(JsonPath path, JsonToken token) {
        return handlerStorage.getValueHandlers(path, token).stream().map(contextJsonNodeBiConsumer -> new Consumer<JsonNode>() {
            @Override
            public void accept(JsonNode jsonNode) {
                contextJsonNodeBiConsumer.accept(context, jsonNode);
            }
        }).collect(Collectors.toList());
    }

    @Override
    public Collection<Runnable> getStartValueHandler(JsonPath path, JsonToken token) {
        return handlerStorage.getStartValueHandlers(path, token).stream().map(consumer -> new Runnable() {
            @Override
            public void run() {
                consumer.accept(context);
            }
        }).collect(Collectors.toList());
    }

    @Override
    public Collection<Runnable> getEndValueHandler(JsonPath path, JsonToken token) {
        return handlerStorage.getEndValueHandlers(path, token).stream().map(consumer -> new Runnable() {
            @Override
            public void run() {
                consumer.accept(context);
            }
        }).collect(Collectors.toList());
    }
}
