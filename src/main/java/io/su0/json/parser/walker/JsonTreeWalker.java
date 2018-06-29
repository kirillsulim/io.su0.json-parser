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

    public static void walk(InputStream inputStream, HandlerStorage handlerStorage) throws IOException {
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
                    processHandlers(handlerStorage.getHandlers(jsonPath, jsonToken), jsonPath, parser);
                    processJsonNodeHandlers(handlerStorage.getJsonNodeHandlers(jsonPath, jsonToken), jsonPath, parser);
                    break;
                case VALUE_EMBEDDED_OBJECT:
                case NOT_AVAILABLE:
                    throw new IllegalStateException();
            }
        }
    }

    private static void processHandlers(Collection<JsonValueHandler> handlers, JsonPath path, JsonParser parser) throws IOException {
        for (JsonValueHandler handler : handlers) {
            if (handler instanceof StringJsonValueHandler) {
                ((StringJsonValueHandler) handler).handle(path, parser.getValueAsString());
            }
            else if (handler instanceof IntJsonValueHandler) {
                ((IntJsonValueHandler) handler).handle(path, parser.getIntValue());
            }
            else if (handler instanceof LongJsonValueHandler) {
                ((LongJsonValueHandler) handler).handle(path, parser.getLongValue());
            }
            else if (handler instanceof FloatJsonValueHandler) {
                ((FloatJsonValueHandler) handler).handle(path, parser.getFloatValue());
            }
            else if (handler instanceof DoubleJsonValueHandler) {
                ((DoubleJsonValueHandler) handler).handle(path, parser.getDoubleValue());
            }
            else if (handler instanceof BooleanJsonValueHandler) {
                ((BooleanJsonValueHandler) handler).handle(path, parser.getBooleanValue());
            }
            else {
                throw new IllegalStateException("Unknown handler type + " + handler.getClass().getName());
            }
        }
    }

    private static void processJsonNodeHandlers(Collection<JsonNodeValueHandler> handlers, JsonPath path, JsonParser parser) throws IOException {
        if (handlers.isEmpty()) {
            return;
        }
        JsonNode treeNode = objectMapper.readTree(parser);
        for (JsonNodeValueHandler handler : handlers) {
            handler.handle(path, treeNode);
        }
    }
}
