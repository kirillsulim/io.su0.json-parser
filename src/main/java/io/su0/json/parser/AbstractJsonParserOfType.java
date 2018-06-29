package io.su0.json.parser;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class AbstractJsonParserOfType<Type> extends AbstractJsonParserOfTypeWithBuilder<Type, Type> {

    public AbstractJsonParserOfType(Supplier<Type> creator) {
        super(creator, Function.identity());
    }
}
