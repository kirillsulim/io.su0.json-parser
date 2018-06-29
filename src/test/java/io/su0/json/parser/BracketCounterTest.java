package io.su0.json.parser;

import io.su0.json.parser.walker.BracketCounter;
import org.junit.Test;

import static org.junit.Assert.*;

public class BracketCounterTest {

    @Test
    public void shouldReturnFalseOnEmptyPop() {
        BracketCounter counter = new BracketCounter();

        assertFalse(counter.popObject());
        assertFalse(counter.popArray());
    }

    @Test
    public void shouldReturnFalseOnEmptyIn() {
        BracketCounter counter = new BracketCounter();

        assertFalse(counter.inObject());
        assertFalse(counter.inArray());
    }

    @Test
    public void shouldReturnTrueOnSuccessfulPop() {
        BracketCounter counter = new BracketCounter();

        counter.pushObject();
        counter.pushArray();

        assertTrue(counter.popArray());
        assertTrue(counter.popObject());
    }

    @Test
    public void shouldReturnFalseOnBadPop() {
        BracketCounter counter = new BracketCounter();

        counter.pushObject();
        assertFalse(counter.popArray());

        counter.pushArray();
        assertFalse(counter.popObject());
    }
}
