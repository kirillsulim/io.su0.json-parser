package io.su0.json.parser.benchmark.parsers;

import io.su0.json.parser.benchmark.model.Pojo;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;

public class SimpleJsonPojoParser {

    private JSONParser parser = new JSONParser();

    public Pojo parse(InputStream inputStream) throws Exception {
        JSONObject json = (JSONObject) parser.parse(new InputStreamReader(inputStream));

        Pojo pojo = new Pojo();
        pojo.setStringValue((String) json.get("stringValue"));
        pojo.setIntValue(((Long) json.get("intValue")).intValue());

        return pojo;
    }
}
