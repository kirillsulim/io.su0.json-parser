package io.su0.json.parser.benchmark.small;

import io.su0.json.parser.AbstractJsonParserOfType;

public class Su0PojoParser extends AbstractJsonParserOfType<Pojo> {

    public Su0PojoParser() {
        super(Pojo::new);
        add("$.stringValue", (pojo, jsonNode) -> pojo.setStringValue(jsonNode.asText()));
        add("$.intValue", (pojo, jsonNode) -> pojo.setIntValue(jsonNode.intValue()));
    }
}
