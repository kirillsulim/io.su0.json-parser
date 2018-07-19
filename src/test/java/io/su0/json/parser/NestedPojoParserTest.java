package io.su0.json.parser;

import io.su0.json.TestUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NestedPojoParserTest {

    private static class Nested {
        private String stringField;
        private int intField;

        public String getStringField() {
            return stringField;
        }

        public void setStringField(String stringField) {
            this.stringField = stringField;
        }

        public int getIntField() {
            return intField;
        }

        public void setIntField(int intField) {
            this.intField = intField;
        }
    }

    private static class Container {
        private Nested nested;
        private boolean booleanField;
        private List<Nested> list;

        public Nested getNested() {
            return nested;
        }

        public void setNested(Nested nested) {
            this.nested = nested;
        }

        public boolean isBooleanField() {
            return booleanField;
        }

        public void setBooleanField(boolean booleanField) {
            this.booleanField = booleanField;
        }

        public List<Nested> getList() {
            return list;
        }

        public void setList(List<Nested> list) {
            this.list = list;
        }
    }

    private static class NestedParser extends AbstractJsonParserOfType<Nested> {
        public NestedParser() {
            super(Nested::new);
            add("$.stringField", (nested, jsonNode) -> nested.setStringField(jsonNode.asText()));
            add("$.intField", (nested, jsonNode) -> nested.setIntField(jsonNode.asInt()));
        }
    }

    private static class ContainerParser extends AbstractJsonParserOfType<Container> {
        public ContainerParser() {
            super(Container::new);
            add("$.booleanField", (container, jsonNode) -> container.setBooleanField(jsonNode.asBoolean()));
            add("$.nested", Container::setNested, new NestedParser());
            add("$.list", Container::setList, new CollectionParser<>(ArrayList::new, new NestedParser()));
        }
    }

    @Test
    public void shouldParseContainer() throws Exception {
        ContainerParser containerParser = new ContainerParser();
        Container container = containerParser.parse(TestUtil.getResourceAsStream("nested-pojo.json"));
        assertTrue(container.booleanField);
        assertEquals("abc", container.nested.stringField);
        assertEquals(123, container.nested.intField);
        assertEquals(2, container.list.size());
    }
}

