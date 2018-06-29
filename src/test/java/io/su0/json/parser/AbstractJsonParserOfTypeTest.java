package io.su0.json.parser;

import io.su0.json.path.parsing.Facade;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;

public class AbstractJsonParserOfTypeTest {

    private static class SimplePojo {

        private String stringData;
        private int intData;

        public String getStringData() {
            return stringData;
        }

        public void setStringData(String stringData) {
            this.stringData = stringData;
        }

        public int getIntData() {
            return intData;
        }

        public void setIntData(int intData) {
            this.intData = intData;
        }
    }

    private static class SimplePojoParser extends AbstractJsonParserOfType<SimplePojo> {

        public SimplePojoParser() {
            super(SimplePojo::new);
            addStringJsonValueHandler(Facade.parse("$.stringData"), SimplePojo::setStringData);
            addIntJsonValueHandler(Facade.parse("$.intData"), SimplePojo::setIntData);
        }
    }

    @Test
    public void shouldParseSimplePojo() throws Exception {
        SimplePojoParser parser = new SimplePojoParser();
        SimplePojo parsed = parser.parse(toStream("{\"stringData\":\"abc\",\"intData\":123}"));

        assertEquals("abc", parsed.getStringData());
        assertEquals(123, parsed.getIntData());
    }


    private static InputStream toStream(String string) {
        return new ByteArrayInputStream(string.getBytes());
    }
}
