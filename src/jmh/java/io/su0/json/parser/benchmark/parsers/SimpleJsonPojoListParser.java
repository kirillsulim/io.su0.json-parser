package io.su0.json.parser.benchmark.parsers;

import io.su0.json.parser.benchmark.model.Pojo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class SimpleJsonPojoListParser {

    private JSONParser parser = new JSONParser();

    public LinkedList<Pojo> parse(InputStream inputStream) throws Exception {
        JSONArray json = (JSONArray) parser.parse(new InputStreamReader(inputStream));

        LinkedList<Pojo> result = new LinkedList<>();

        for (Object current : json) {
            JSONObject jsonObject = (JSONObject) current;
            Pojo pojo = new Pojo();
            pojo.setStringValue((String) jsonObject.get("stringValue"));
            pojo.setIntValue(((Long) jsonObject.get("intValue")).intValue());
            result.add(pojo);
        }

        return result;
    }
}
