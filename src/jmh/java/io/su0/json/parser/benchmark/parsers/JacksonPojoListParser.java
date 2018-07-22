package io.su0.json.parser.benchmark.parsers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.su0.json.parser.benchmark.model.Pojo;

import java.io.InputStream;
import java.util.List;

public class JacksonPojoListParser {

    private ObjectMapper objectMapper = new ObjectMapper();

    public List<Pojo> parse(InputStream stream) throws Exception {
        return objectMapper.readValue(stream, new TypeReference<List<Pojo>>(){});
    }
}
