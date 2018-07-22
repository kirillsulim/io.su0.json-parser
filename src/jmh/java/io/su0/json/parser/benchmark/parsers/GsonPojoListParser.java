package io.su0.json.parser.benchmark.parsers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.su0.json.parser.benchmark.model.Pojo;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

public class GsonPojoListParser {

    private Gson gson = new Gson();

    public List<Pojo> parse(InputStream stream) {
        Type listType = new TypeToken<LinkedList<Pojo>>() {
        }.getType();

        return gson.fromJson(new InputStreamReader(stream), listType);
    }
}
