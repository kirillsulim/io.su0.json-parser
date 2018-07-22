package io.su0.json.parser.benchmark.parsers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.su0.json.parser.benchmark.model.Pojo;

import java.io.InputStream;

public class JacksonPojoParser {

    private ObjectMapper objectMapper = new ObjectMapper();

    public Pojo parse(InputStream stream) throws Exception {
        return objectMapper.readValue(stream, Pojo.class);
    }
}
