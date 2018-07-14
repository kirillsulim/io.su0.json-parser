package io.su0.json.parser;

import com.fasterxml.jackson.databind.JsonNode;
import io.su0.json.parser.walker.Context;
import io.su0.json.parser.walker.HandlerStorageImpl;
import io.su0.json.parser.walker.JsonTreeWalker;
import io.su0.json.path.matcher.JsonPathMatcher;
import io.su0.json.path.parsing.Facade;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class AbstractJsonParserOfTypeWithBuilder<Type, TypeBuilder> {

    protected final Supplier<TypeBuilder> builderCreator;
    protected final Function<TypeBuilder, Type> buildFunction;

    protected final HandlerStorageImpl handlerStorage = new HandlerStorageImpl();


    public AbstractJsonParserOfTypeWithBuilder(
            Supplier<TypeBuilder> builderCreator,
            Function<TypeBuilder, Type> buildFunction
    ) {
        this.builderCreator = builderCreator;
        this.buildFunction = buildFunction;
    }

    public Type parse(InputStream inputStream) throws IOException {
        Context context = new Context();
        context.push(builderCreator.get());

        JsonTreeWalker.walk(inputStream, handlerStorage, context);

        return buildFunction.apply(context.pop());
    }

    public void add(String expr, BiConsumer<TypeBuilder, JsonNode> consumer) {
        handlerStorage.addValueHandler(Facade.parse(expr), (context, jsonNode) -> consumer.accept(context.peek(), jsonNode));
    }

    public <NestedType, NestedTypeBuilder> void add(String expr, BiConsumer<TypeBuilder, NestedType> consumer, AbstractJsonParserOfTypeWithBuilder<NestedType, NestedTypeBuilder> inner) {
        JsonPathMatcher matcher = Facade.parse(expr);

        handlerStorage.append(matcher, inner.handlerStorage);

        handlerStorage.addStartObjectHandler(matcher, context -> context.push(inner.builderCreator.get()));
        handlerStorage.addStartArrayHandler(matcher, context -> context.push(inner.builderCreator.get()));
        handlerStorage.addStartValueHandler(matcher, context -> context.push(inner.builderCreator.get()));
        handlerStorage.addEndObjectHandler(matcher, context -> {
            NestedTypeBuilder pop = context.pop();
            consumer.accept(context.peek(), inner.buildFunction.apply(pop));
        });
        handlerStorage.addEndArrayHandler(matcher, context -> {
            NestedTypeBuilder pop = context.pop();
            consumer.accept(context.peek(), inner.buildFunction.apply(pop));
        });
        handlerStorage.addEndValueHandler(matcher, context -> {
            NestedTypeBuilder pop = context.pop();
            consumer.accept(context.peek(), inner.buildFunction.apply(pop));
        });
    }
}
