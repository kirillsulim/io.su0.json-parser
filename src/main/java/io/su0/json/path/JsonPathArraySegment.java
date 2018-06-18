package io.su0.json.path;

public class JsonPathArraySegment implements JsonPathSegment {

    private int index = 0;

    public JsonPathArraySegment(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void increment() {
        ++index;
    }
}
