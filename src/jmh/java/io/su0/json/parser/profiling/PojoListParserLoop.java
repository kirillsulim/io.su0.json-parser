package io.su0.json.parser.profiling;

import io.su0.json.parser.benchmark.BenchmarkUtil;
import io.su0.json.parser.benchmark.model.Pojo;
import io.su0.json.parser.benchmark.parsers.Su0PojoListParser;

import java.io.ByteArrayInputStream;
import java.util.LinkedList;

public class PojoListParserLoop {

    private static final Su0PojoListParser parser = new Su0PojoListParser();

    private static final byte[] data = BenchmarkUtil.getResource("pojo-collection.json");

    public static void main(String[] args) throws Exception {
        while (true) {
            LinkedList<Pojo> list = parser.parse(new ByteArrayInputStream(data));
            System.out.println(list.size());
        }
    }
}
