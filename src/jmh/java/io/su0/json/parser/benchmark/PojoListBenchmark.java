package io.su0.json.parser.benchmark;

import io.su0.json.parser.benchmark.model.Pojo;
import io.su0.json.parser.benchmark.parsers.GsonPojoListParser;
import io.su0.json.parser.benchmark.parsers.JacksonPojoListParser;
import io.su0.json.parser.benchmark.parsers.SimpleJsonPojoListParser;
import io.su0.json.parser.benchmark.parsers.Su0PojoListParser;
import org.openjdk.jmh.annotations.*;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PojoListBenchmark {

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        Su0PojoListParser su0PojoListParser = new Su0PojoListParser();
        SimpleJsonPojoListParser simpleJsonPojoListParser = new SimpleJsonPojoListParser();
        JacksonPojoListParser jacksonPojoListParser = new JacksonPojoListParser();
        GsonPojoListParser gsonPojoListParser = new GsonPojoListParser();

        byte[] data = BenchmarkUtil.getResource("pojo-collection.json");
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public List<Pojo> su0Parser(BenchmarkState state) throws Exception {
        return state.su0PojoListParser.parse(new ByteArrayInputStream(state.data));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public List<Pojo> simpleJsonParser(BenchmarkState state) throws Exception {
        return state.simpleJsonPojoListParser.parse(new ByteArrayInputStream(state.data));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public List<Pojo> jacksonParser(BenchmarkState state) throws Exception {
        return state.jacksonPojoListParser.parse(new ByteArrayInputStream(state.data));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public List<Pojo> gsonParser(BenchmarkState state) throws Exception {
        return state.gsonPojoListParser.parse(new ByteArrayInputStream(state.data));
    }
}
