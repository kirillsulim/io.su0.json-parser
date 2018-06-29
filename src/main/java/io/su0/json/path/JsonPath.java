package io.su0.json.path;

import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class JsonPath {

    private final Deque<JsonPathSegment> segments = new LinkedList<>();

    public void enterObject() {
        segments.addLast(JsonPathFieldSegment.EMPTY);
    }

    public void leaveObject() {
        segments.pollLast();
    }

    public void enterField(String fieldName) {
        segments.pollLast();
        segments.addLast(new JsonPathFieldSegment(fieldName));
    }

    public void enterArray() {
        segments.addLast(new JsonPathArraySegment());
    }

    public void leaveArray() {
        segments.pollLast();
    }

    public void nextArrayElement() {
        ((JsonPathArraySegment) segments.peekLast()).inc();
    }

    public Deque<JsonPathSegment> getSegments() {
        return segments;
    }

    @Override
    public String toString() {
        return "$" + segments.stream()
                .map(segment -> {
                    if (segment instanceof JsonPathFieldSegment) {
                        return "." + ((JsonPathFieldSegment) segment).getFieldName();
                    } else if (segment instanceof JsonPathArraySegment) {
                        return "[" + String.valueOf(((JsonPathArraySegment) segment).getIndex()) + "]";
                    }
                    throw new IllegalStateException();
                })
                .collect(Collectors.joining());
    }
}
