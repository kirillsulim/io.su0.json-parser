package io.su0.json.parser;

import java.util.Collection;
import java.util.function.Supplier;

public class CollectionParser<Type, CollectionType extends Collection<Type>> extends AbstractJsonParserOfType<CollectionType> {

    public <NestedBuilder> CollectionParser(Supplier<CollectionType> supplier, AbstractJsonParserOfTypeWithBuilder<Type, NestedBuilder> elementParser) {
        super(supplier);

        add("$[0]", CollectionType::add, elementParser);
        add("$[1]", CollectionType::add, elementParser);
        add("$[2]", CollectionType::add, elementParser);
    }
}
