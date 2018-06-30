package io.su0.json.parser;

import io.su0.json.TestUtil;
import io.su0.json.path.parsing.Facade;
import org.junit.Assert;
import org.junit.Test;

import java.util.function.Supplier;

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
    }

    private static class NestedParser extends AbstractJsonParserOfType<Nested> {
        public NestedParser() {
            super(Nested::new);
            add("$.stringField", Nested::setStringField);
            add("$.intField", Nested::setIntField);
        }
    }

    private static class ContainerParser extends AbstractJsonParserOfType<Container> {
        public ContainerParser() {
            super(Container::new);
            add("$.booleanField", Container::setBooleanField);
            add("$.nested", Container::setNested, new NestedParser());
            //handle("$.list", Container::setList, new CollectionParser(ArrayList::new, new ElementParser()));
        }
    }

    @Test
    public void shouldParseContainer() throws Exception {
        ContainerParser containerParser = new ContainerParser();
        Container container = containerParser.parse(TestUtil.getResourceAsStream("nested-pojo.json"));
        Assert.assertTrue(container.booleanField);
    }
}

