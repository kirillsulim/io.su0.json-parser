package io.su0.json.parser;

import io.su0.json.parser.handlers.BooleanJsonValueHandler;
import io.su0.json.parser.walker.HandlerStorage;
import io.su0.json.parser.walker.JsonTreeWalker;
import io.su0.json.path.matcher.JsonPathMatcher;

import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractJsonHandler<Meta> {

    private final HandlerStorage<Meta> handlerStorage;

    public AbstractJsonHandler() {
        this.handlerStorage = new HandlerStorage<>();
    }

    protected void handleInputStream(InputStream inputStream, Meta meta) throws IOException {
        JsonTreeWalker.walk(handlerStorage, inputStream, meta);
    }

    protected void addBooleanJsonValueHandler(JsonPathMatcher matcher, BooleanJsonValueHandler<Meta> handler) {
        handlerStorage.addHandler(matcher, handler);
    }
}
