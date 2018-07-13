package io.su0.json.parser;

import java.util.Collection;
import java.util.function.Supplier;

public class CollectionParser<Type, CollectionType extends Collection<Type>> extends AbstractJsonParserOfType<CollectionType> {

    public <NestedBuilder> CollectionParser(Supplier<CollectionType> supplier, AbstractJsonParserOfTypeWithBuilder<Type, NestedBuilder> elementParser) {
        super(supplier);

        add("$[*]", CollectionType::add, elementParser);
    }
}
