package io.su0.json.parser;

import com.fasterxml.jackson.databind.JsonNode;
import io.su0.json.parser.handlers.IntJsonValueHandler;
import io.su0.json.parser.handlers.JsonNodeValueHandler;
import io.su0.json.parser.handlers.StringJsonValueHandler;
import io.su0.json.parser.walker.HandlerStorage;
import io.su0.json.parser.walker.JsonTreeWalker;
import io.su0.json.path.JsonPath;
import io.su0.json.path.parsing.Facade;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class JsonTreeWalkerTest {

    @Test
    public void name2() throws Exception {
        JsonTreeWalker jsonTreeWalker = new JsonTreeWalker();

        HandlerStorage handlerStorage = new HandlerStorage();
        handlerStorage.addHandler(
                Facade.parse("$.field[4].a.d"),
                new JsonNodeValueHandler() {
                    @Override
                    public void handle(JsonPath path, JsonNode jsonNode) {
                        System.out.println(jsonNode);
                    }
                }
        );
        handlerStorage.addHandler(
                Facade.parse("$.field[4].a.d"),
                new IntJsonValueHandler() {
                    @Override
                    public void handle(JsonPath path, int value) {
                        System.out.println(value);
                    }
                }
        );
        handlerStorage.addHandler(
                Facade.parse("$.field[4].a.d"),
                new JsonNodeValueHandler() {
                    @Override
                    public void handle(JsonPath path, JsonNode jsonNode) {
                        System.out.println(jsonNode);
                    }
                }
        );
        handlerStorage.addHandler(
                Facade.parse("$.field[4].a.d"),
                new StringJsonValueHandler() {
                    @Override
                    public void handle(JsonPath path, String value) {
                        System.out.println(value);
                    }
                }
        );

        jsonTreeWalker.walk(
                toStream("{\"field\":[{\"a\":123}, {\"a\":\"abc\"},{\"a\":true}, {\"a\":null},{\"a\":{\"d\":123}}]}"),
                handlerStorage
        );
    }

    private static InputStream toStream(String string) {
        return new ByteArrayInputStream(string.getBytes());
    }
}
