package io.su0.json.parser.walker;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.su0.json.path.JsonPath;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class JsonTreeWalker {

    private static final JsonFactory factory = new JsonFactory();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void walk(InputStream inputStream, HandlerStorage handlerStorage, Context context) throws IOException {
        JsonParser parser = factory.createParser(inputStream);
        walk(parser, handlerStorage, context);
    }

    public static void walk(JsonParser parser, HandlerStorage handlerStorage, Context context) throws IOException {
        JsonPath jsonPath = new JsonPath();
        BracketCounter bracketCounter = new BracketCounter();

        while (Objects.nonNull(parser.nextToken())) {
            JsonToken jsonToken = parser.currentToken();

            if (bracketCounter.inArray() && (jsonToken.isStructStart() || jsonToken.isScalarValue())) {
                jsonPath.nextArrayElement();
            }

            switch (jsonToken) {
                case START_OBJECT:
                    handlerStorage.getHandlers(jsonPath, jsonToken).forEach(c -> c.accept(context));
                    jsonPath.enterObject();
                    bracketCounter.pushObject();
                    break;
                case END_OBJECT:
                    jsonPath.leaveObject();
                    if (!bracketCounter.popObject()) {
                        throw new IllegalStateException();
                    }
                    handlerStorage.getHandlers(jsonPath, jsonToken).forEach(c -> c.accept(context));
                    break;
                case START_ARRAY:
                    handlerStorage.getHandlers(jsonPath, jsonToken).forEach(c -> c.accept(context));
                    jsonPath.enterArray();
                    bracketCounter.pushArray();
                    break;
                case END_ARRAY:
                    jsonPath.leaveArray();
                    if (!bracketCounter.popArray()) {
                        throw new IllegalStateException();
                    }
                    handlerStorage.getHandlers(jsonPath, jsonToken).forEach(c -> c.accept(context));
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
                    JsonNode node = objectMapper.readTree(parser);
                    handlerStorage.getStartValueHandlers(jsonPath, jsonToken).forEach(c -> c.accept(context));
                    handlerStorage.getValueHandlers(jsonPath, jsonToken).forEach(c -> c.accept(context, node));
                    handlerStorage.getEndValueHandlers(jsonPath, jsonToken).forEach(c -> c.accept(context));
                    break;
                case VALUE_EMBEDDED_OBJECT:
                case NOT_AVAILABLE:
                    throw new IllegalStateException();
            }
        }
    }
}
