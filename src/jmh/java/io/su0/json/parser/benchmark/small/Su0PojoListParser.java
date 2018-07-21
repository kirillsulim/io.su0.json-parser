package io.su0.json.parser.benchmark.small;

import io.su0.json.parser.CollectionParser;

import java.util.ArrayList;

public class Su0PojoListParser extends CollectionParser<Pojo, ArrayList<Pojo>> {

    public Su0PojoListParser() {
        super(ArrayList::new, new Su0PojoParser());
    }
}
