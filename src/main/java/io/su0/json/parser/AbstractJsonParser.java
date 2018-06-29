package io.su0.json.parser;

import java.util.function.Function;
import java.util.function.Supplier;

public class AbstractJsonParser<Type> extends AbstractJsonParserWithBuilder<Type, Type> {

    public AbstractJsonParser(Supplier<Type> creator) {
        super(creator, Function.identity());
    }
}
