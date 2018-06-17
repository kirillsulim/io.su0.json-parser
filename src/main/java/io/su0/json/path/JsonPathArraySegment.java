package io.su0.json.path;

public class JsonPathArraySegment {

    private int index = 0;

    public JsonPathArraySegment() {
    }

    public int getIndex() {
        return index;
    }

    public void increment() {
        ++index;
    }
}
