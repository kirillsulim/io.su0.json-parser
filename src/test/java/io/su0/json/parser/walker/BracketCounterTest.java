package io.su0.json.parser.walker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


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
