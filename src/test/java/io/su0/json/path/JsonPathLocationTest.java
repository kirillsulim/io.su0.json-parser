package io.su0.json.path;

import org.junit.Test;

public class JsonPathLocationTest {

    @Test
    public void shouldEnterLeaveObject() {
        JsonPath location = new JsonPath();
        location.enterObject();
        location.leaveObject();
    }
}
