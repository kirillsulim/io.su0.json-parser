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


    }

    private static InputStream toStream(String string) {
        return new ByteArrayInputStream(string.getBytes());
    }
}
