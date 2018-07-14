package io.su0.json.parser;

import io.su0.json.TestUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
            add("$.stringData", (simplePojo, jsonNode) -> simplePojo.setStringData(jsonNode.asText()));
            add("$.intData", (simplePojo, jsonNode) -> simplePojo.setIntData(jsonNode.asInt()));
        }
    }

    private static class SimplePojoRootValueParser extends AbstractJsonParserOfType<SimplePojo> {
        public SimplePojoRootValueParser() {
            super(SimplePojo::new);
            add("$", (simplePojo, jsonNode) -> simplePojo.setStringData(jsonNode.asText()));
        }
    }

    @Test
    public void shouldParseSimplePojo() throws Exception {
        SimplePojoParser parser = new SimplePojoParser();
        SimplePojo parsed = parser.parse(TestUtil.getResourceAsStream("simple-pojo.json"));

        assertEquals("abc", parsed.getStringData());
        assertEquals(123, parsed.getIntData());
    }

    @Test
    public void shouldParseRootValue() throws Exception {
        SimplePojoRootValueParser parser = new SimplePojoRootValueParser();
        SimplePojo parsed = parser.parse(TestUtil.getResourceAsStream("string-value.json"));

        assertEquals("abc", parsed.stringData);
    }
}
