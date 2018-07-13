package io.su0.json.parser.walker;

import java.util.Deque;
import java.util.LinkedList;

public class Context {

    private final Deque deque = new LinkedList();

    public <T> void push(T t) {
        deque.push(t);
    }

    public <T> T pop() {
        return (T) deque.pop();
    }

    public <T> T peek() {
        return (T) deque.peek();
    }
}
