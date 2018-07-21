package io.su0.json.parser.benchmark.small;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Su0ParserBenchmark {

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        public Su0PojoParser su0Parser = new Su0PojoParser();
        public Su0PojoListParser su0PojoListParser = new Su0PojoListParser();
        public JSONParser simpleJsonParser = new JSONParser();
        public byte[] data = getResource("pojo-collection.json");
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void su0Parser(BenchmarkState state, Blackhole blackhole) throws Exception {
        ArrayList<Pojo> result = state.su0PojoListParser.parse(new ByteArrayInputStream(state.data));

        blackhole.consume(result);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void simpleJsonParser(BenchmarkState state, Blackhole blackhole) throws Exception {
        JSONArray json = (JSONArray) state.simpleJsonParser.parse(new InputStreamReader(new ByteArrayInputStream(state.data)));

        ArrayList<Pojo> result = new ArrayList<>();

        json.forEach(o -> {
            JSONObject current = (JSONObject) o;
            Pojo pojo = new Pojo();
            pojo.setStringValue((String) current.get("stringValue"));
            pojo.setIntValue(((Long) current.get("intValue")).intValue());
            result.add(pojo);
        });

        blackhole.consume(result);
    }

    private static byte[] getResource(String name) {
        try {
            return BenchmarkState.class.getClassLoader()
                    .getResourceAsStream(name)
                    .readAllBytes();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
