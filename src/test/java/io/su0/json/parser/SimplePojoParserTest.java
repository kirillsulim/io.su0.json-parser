package io.su0.json.parser;

import io.su0.json.TestUtil;
import io.su0.json.path.parsing.Facade;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;

public class SimplePojoParserTest {

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
            add("$.stringData", SimplePojo::setStringData);
            add("$.intData", SimplePojo::setIntData);
        }
    }

    @Test
    public void shouldParseSimplePojo() throws Exception {
        SimplePojoParser parser = new SimplePojoParser();
        SimplePojo parsed = parser.parse(TestUtil.getResourceAsStream("simple-pojo.json"));

        assertEquals("abc", parsed.getStringData());
        assertEquals(123, parsed.getIntData());
    }
}
