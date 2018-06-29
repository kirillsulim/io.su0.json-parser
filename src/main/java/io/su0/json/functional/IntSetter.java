package io.su0.json.functional;

@FunctionalInterface
public interface IntSetter<T> {

    void set(T t, int value);
}
