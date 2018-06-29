package io.su0.json.parser.walker;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.su0.json.parser.handlers.*;
import io.su0.json.path.JsonPath;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Objects;

public class JsonTreeWalker {

    private static final JsonFactory factory = new JsonFactory();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <Meta> void walk(HandlerStorage<Meta> handlerStorage, InputStream inputStream, Meta meta) throws IOException {
        JsonPath jsonPath = new JsonPath();
        BracketCounter bracketCounter = new BracketCounter();

        JsonParser parser = factory.createParser(inputStream);

        while (Objects.nonNull(parser.nextToken())) {
            JsonToken jsonToken = parser.currentToken();

            switch (jsonToken) {
                case START_OBJECT:
                    if (bracketCounter.inArray()) {
                        jsonPath.nextArrayElement();
                    }
                    jsonPath.enterObject();
                    bracketCounter.pushObject();
                    break;
                case END_OBJECT:
                    jsonPath.leaveObject();
                    if (!bracketCounter.popObject()) {
                        throw new IllegalStateException();
                    }
                    break;
                case START_ARRAY:
                    if (bracketCounter.inArray()) {
                        jsonPath.nextArrayElement();
                    }
                    jsonPath.enterArray();
                    bracketCounter.pushArray();
                    break;
                case END_ARRAY:
                    jsonPath.leaveArray();
                    if (!bracketCounter.popArray()) {
                        throw new IllegalStateException();
                    }
                    break;
                case FIELD_NAME:
                    jsonPath.enterField(parser.getCurrentName());
                    break;
                case VALUE_NULL:
                case VALUE_TRUE:
                case VALUE_FALSE:
                case VALUE_STRING:
                case VALUE_NUMBER_INT:
                case VALUE_NUMBER_FLOAT:
                    if (bracketCounter.inArray()) {
                        jsonPath.nextArrayElement();
                    }
                    processHandlers(handlerStorage.getHandlers(jsonPath, jsonToken), jsonPath, parser, meta);
                    processJsonNodeHandlers(handlerStorage.getJsonNodeHandlers(jsonPath, jsonToken), jsonPath, parser, meta);
                    break;
                case VALUE_EMBEDDED_OBJECT:
                case NOT_AVAILABLE:
                    throw new IllegalStateException();
            }
        }
    }

    private static <Meta> void processHandlers(Collection<JsonValueHandler<Meta>> handlers, JsonPath path, JsonParser parser, Meta meta) throws IOException {
        for (JsonValueHandler<Meta> handler : handlers) {
            if (handler instanceof StringJsonValueHandler) {
                ((StringJsonValueHandler<Meta>) handler).handle(path, parser.getValueAsString(), meta);
            }
            else if (handler instanceof IntJsonValueHandler) {
                ((IntJsonValueHandler<Meta>) handler).handle(path, parser.getIntValue(), meta);
            }
            else if (handler instanceof LongJsonValueHandler) {
                ((LongJsonValueHandler<Meta>) handler).handle(path, parser.getLongValue(), meta);
            }
            else if (handler instanceof FloatJsonValueHandler) {
                ((FloatJsonValueHandler<Meta>) handler).handle(path, parser.getFloatValue(), meta);
            }
            else if (handler instanceof DoubleJsonValueHandler) {
                ((DoubleJsonValueHandler<Meta>) handler).handle(path, parser.getDoubleValue(), meta);
            }
            else if (handler instanceof BooleanJsonValueHandler) {
                ((BooleanJsonValueHandler<Meta>) handler).handle(path, parser.getBooleanValue(), meta);
            }
            else {
                throw new IllegalStateException("Unknown handler type + " + handler.getClass().getName());
            }
        }
    }

    private static <Meta> void processJsonNodeHandlers(Collection<JsonNodeValueHandler<Meta>> handlers, JsonPath path, JsonParser parser, Meta meta) throws IOException {
        if (handlers.isEmpty()) {
            return;
        }
        JsonNode treeNode = objectMapper.readTree(parser);
        for (JsonNodeValueHandler<Meta> handler : handlers) {
            ((JsonNodeValueHandler<Meta>) handler).handle(path, treeNode, meta);
        }
    }
}
