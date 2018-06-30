package io.su0.json.parser;

import com.fasterxml.jackson.core.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class AbstractJsonParserOfTypeWithBuilder<Type, TypeBuilder> extends AbstractJsonParser<TypeBuilder> implements Parser<Type> {

    protected final Supplier<TypeBuilder> builderCreator;
    protected final Function<TypeBuilder, Type> buildFunction;


    public AbstractJsonParserOfTypeWithBuilder(
            Supplier<TypeBuilder> builderCreator,
            Function<TypeBuilder, Type> buildFunction
    ) {
        this.builderCreator = builderCreator;
        this.buildFunction = buildFunction;
    }

    @Override
    public Type parse(InputStream inputStream) throws IOException {
        TypeBuilder accumulator = builderCreator.get();

        handleInS(inputStream, accumulator);

        return buildFunction.apply(accumulator);
    }

    public Type parse(JsonParser parser) throws IOException {
        TypeBuilder accumulator = builderCreator.get();

        handlePs(parser, accumulator);

        return buildFunction.apply(accumulator);

    }


}
