package io.su0.json.path.parsing;

import io.su0.json.path.matcher.*;
import io.su0.json.path.parsing.dfa.DeterministicFiniteAutomaton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Parser {

    public static JsonPathMatcher parse(Iterable<Token> tokens) {
        Iterator<Token> iterator = tokens.iterator();

        // LinkedList<JsonPathSegmentMatcher> segments = new LinkedList<>();

        DeterministicFiniteAutomaton dfa = new DeterministicFiniteAutomaton();

        while (iterator.hasNext()) {
            dfa.accept(iterator.next());
        }

        return new JsonPathMatcher(dfa.finish());
    }
}
