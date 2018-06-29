package io.su0.json.functional;

@FunctionalInterface
public interface BooleanSetter<T> {

    void set(T t, boolean value);
}
