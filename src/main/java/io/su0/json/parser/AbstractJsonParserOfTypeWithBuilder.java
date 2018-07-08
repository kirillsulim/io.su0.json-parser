package io.su0.json.parser;

import com.fasterxml.jackson.databind.JsonNode;
import io.su0.json.parser.walker.HandlerStorageImpl;
import io.su0.json.parser.walker.HandlerStorageWithContext;
import io.su0.json.parser.walker.JsonTreeWalker;
import io.su0.json.path.matcher.JsonPathMatcher;
import io.su0.json.path.parsing.Facade;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class AbstractJsonParserOfTypeWithBuilder<Type, TypeBuilder> implements Parser<Type> {

    protected final Supplier<TypeBuilder> builderCreator;
    protected final Function<TypeBuilder, Type> buildFunction;

    protected final HandlerStorageImpl<TypeBuilder> handlerStorage = new HandlerStorageImpl<>();


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

        HandlerStorageWithContext handlerStorageWithContext = new HandlerStorageWithContext<>(handlerStorage, accumulator);

        JsonTreeWalker.walk(inputStream, handlerStorageWithContext);

        return buildFunction.apply(accumulator);
    }

    public void add(String expr, BiConsumer<TypeBuilder, JsonNode> consumer) {
        handlerStorage.addValueHandler(Facade.parse(expr), consumer);
    }

    public <NestedType, NestedTypeBuilder> void add(String expr, BiConsumer<TypeBuilder, NestedType> consumer, AbstractJsonParserOfTypeWithBuilder<NestedType, NestedTypeBuilder> inner) {
        JsonPathMatcher matcher = Facade.parse(expr);

        Ref<NestedTypeBuilder> ref = new Ref<>();

        handlerStorage.append(matcher, inner.handlerStorage, ref);

        handlerStorage.addStartObjectHandler(matcher, typeBuilder -> ref.setRef(inner.builderCreator.get()));
        handlerStorage.addEndObjectHandler(matcher, typeBuilder -> consumer.accept(typeBuilder, inner.buildFunction.apply(ref.getRef())));
    }
}
