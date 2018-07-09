package io.su0.json.parser;

import com.fasterxml.jackson.core.JsonParser;
import io.su0.json.TestUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.function.Supplier;

import static org.junit.Assert.*;

public class CollectionParserTest {

    private static class IntWrapper {
        int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    private static class IntWrapperParser extends AbstractJsonParserOfType<IntWrapper> {

        public IntWrapperParser() {
            super(IntWrapper::new);
            add("$.value", (intWrapper, jsonNode) -> intWrapper.setValue(jsonNode.intValue()));
        }
    }

    private static class IntegerParser extends AbstractJsonParserOfTypeWithBuilder<Integer, IntWrapper> {
        public IntegerParser() {
            super(IntWrapper::new, IntWrapper::getValue);
            add("$", (intWrapper, jsonNode) -> intWrapper.setValue(jsonNode.intValue()));
        }
    }

    @Test
    public void shouldParseIntCollection() throws Exception {
        CollectionParser<Integer, ArrayList<Integer>> parser = new CollectionParser<>(ArrayList::new, new IntegerParser());

        ArrayList<Integer> parsed = parser.parse(TestUtil.getResourceAsStream("simple-collection.json"));

        assertEquals(3, parsed.size());
        assertEquals(1, (int) parsed.get(0));
        assertEquals(2, (int) parsed.get(1));
        assertEquals(3, (int) parsed.get(2));
    }

    @Test
    public void shouldParseObjectCollection() throws Exception {
        CollectionParser<IntWrapper, ArrayList<IntWrapper>> parser = new CollectionParser<>(ArrayList::new, new IntWrapperParser());

        ArrayList<IntWrapper> parsed = parser.parse(TestUtil.getResourceAsStream("object-collection.json"));

        assertEquals(3, parsed.size());
        assertEquals(1, parsed.get(0).value);
        assertEquals(2, parsed.get(1).value);
        assertEquals(3, parsed.get(2).value);
    }

    @Test
    public void shouldParseNestedCollection() throws Exception {
        CollectionParser<ArrayList<Integer>, ArrayList<ArrayList<Integer>>> parser = new CollectionParser<>(ArrayList::new, new CollectionParser<>(ArrayList::new, new IntegerParser()));

        ArrayList<ArrayList<Integer>> parsed = parser.parse(TestUtil.getResourceAsStream("nested-collection.json"));

        assertEquals(2, parsed.size());
        assertEquals(2, parsed.get(0).size());
        assertEquals(1, (int) parsed.get(0).get(0));
        assertEquals(2, (int) parsed.get(0).get(1));
        assertEquals(2, parsed.get(0).size());
        assertEquals(3, (int) parsed.get(1).get(0));
        assertEquals(4, (int) parsed.get(1).get(1));
    }
}

