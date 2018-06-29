package io.su0.json.parser;

import io.su0.json.functional.BooleanSetter;
import io.su0.json.functional.IntSetter;
import io.su0.json.path.matcher.JsonPathMatcher;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class AbstractJsonParserOfTypeWithBuilder<Type, TypeBuilder> extends AbstractJsonHandler<TypeBuilder> implements Parser<Type> {

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

        handleInputStream(inputStream, accumulator);

        return buildFunction.apply(accumulator);
    }

    protected void addBooleanJsonValueHandler(JsonPathMatcher matcher, BooleanSetter<TypeBuilder> setter) {
        super.addBooleanJsonValueHandler(matcher, (path, value, typeBuilder) -> setter.set(typeBuilder, value));
    }

    protected void addStringJsonValueHandler(JsonPathMatcher matcher, BiConsumer<TypeBuilder, String> setter) {
        super.addStringJsonValueHandler(matcher, (path, value, typeBuilder) -> setter.accept(typeBuilder, value));
    }

    protected void addIntJsonValueHandler(JsonPathMatcher matcher, IntSetter<TypeBuilder> setter) {
        super.addIntJsonValueHandler(matcher, (path, value, typeBuilder) -> setter.set(typeBuilder, value));
    }

}
