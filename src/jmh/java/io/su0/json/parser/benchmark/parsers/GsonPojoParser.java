package io.su0.json.parser.benchmark.parsers;

import com.google.gson.Gson;
import io.su0.json.parser.benchmark.model.Pojo;

import java.io.InputStream;
import java.io.InputStreamReader;

public class GsonPojoParser {

    private Gson gson = new Gson();

    public Pojo parse(InputStream stream) {
        return gson.fromJson(new InputStreamReader(stream), Pojo.class);
    }
}
