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

    public static <Meta> void walk(InputStream inputStream, HandlerStorage<Meta> handlerStorage, Meta meta) throws IOException {
        JsonParser parser = factory.createParser(inputStream);
        walk(parser, handlerStorage, meta);
    }

    public static <Meta> void walk(JsonParser parser, HandlerStorage<Meta> handlerStorage, Meta meta) throws IOException {
        JsonPath jsonPath = new JsonPath();
        BracketCounter bracketCounter = new BracketCounter();

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
                    break;
                case VALUE_EMBEDDED_OBJECT:
                case NOT_AVAILABLE:
                    throw new IllegalStateException();
            }
            for (BlaHandler<Meta> handler : handlerStorage.getHandlers(jsonPath, jsonToken)) {
                handler.handle(parser, jsonPath, jsonToken, meta);
            }
            if (bracketCounter.isEmpty()) {
                break;
            }
        }
    }
}
