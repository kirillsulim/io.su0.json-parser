package io.su0.json.parser.walker;

import java.util.Deque;
import java.util.LinkedList;

public class BracketCounter {

    private enum Bracket {
        OBJECT, // {
        ARRAY; // [
    }

    private final Deque<Bracket> brackets = new LinkedList<>();

    public void pushObject() {
        brackets.push(Bracket.OBJECT);
    }

    public void pushArray() {
        brackets.push(Bracket.ARRAY);
    }

    public boolean popObject() {
        return !brackets.isEmpty() && Bracket.OBJECT == brackets.pop();
    }

    public boolean popArray() {
        return !brackets.isEmpty() && Bracket.ARRAY == brackets.pop();
    }

    public boolean inObject() {
        return !brackets.isEmpty() && Bracket.OBJECT == brackets.peek();
    }

    public boolean inArray() {
        return !brackets.isEmpty() && Bracket.ARRAY == brackets.peek();
    }

    public boolean isEmpty() {
        return brackets.isEmpty();
    }
}
