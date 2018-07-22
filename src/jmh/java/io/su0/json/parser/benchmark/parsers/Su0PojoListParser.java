package io.su0.json.parser.benchmark.parsers;

import io.su0.json.parser.CollectionParser;
import io.su0.json.parser.benchmark.model.Pojo;

import java.util.LinkedList;

public class Su0PojoListParser extends CollectionParser<Pojo, LinkedList<Pojo>> {

    public Su0PojoListParser() {
        super(LinkedList::new, new Su0PojoParser());
    }
}
