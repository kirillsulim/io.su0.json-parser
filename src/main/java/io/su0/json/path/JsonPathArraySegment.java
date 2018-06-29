package io.su0.json.path;

public class JsonPathArraySegment implements JsonPathSegment {

    private int index = -1;

    public void inc() {
        ++index;
    }

    public int getIndex() {
        return index;
    }
}
