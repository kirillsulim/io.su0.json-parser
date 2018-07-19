package io.su0.json.path.parsing.dfa;

import io.su0.json.path.JsonPathArraySegment;
import io.su0.json.path.matcher.JsonPathArraySegmentAnyMatcher;
import io.su0.json.path.matcher.JsonPathArraySegmentMatcher;
import io.su0.json.path.matcher.JsonPathFieldSegmentMatcher;
import io.su0.json.path.matcher.JsonPathSegmentMatcher;
import io.su0.json.path.parsing.Token;
import io.su0.json.path.parsing.TokenType;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class DeterministicFiniteAutomaton {

    private enum State {
        NOT_INITIALIZED,
        PARSING,
        ARRAY_STARTED,
        ARRAY_INDEX_ACQUIRED,
        DOT_ACQUIRED,
        FIELD_NAME_ACQUIRED
    }

    private static class Rule {
        private final State after;
        private final BiConsumer<Token, LinkedList<JsonPathSegmentMatcher>> action;

        public Rule(State after, BiConsumer<Token, LinkedList<JsonPathSegmentMatcher>> action) {
            this.after = after;
            this.action = action;
        }
    }

    private static Set<State> finiteStates = new HashSet<>();
    static {
        finiteStates.add(State.PARSING);
    }

    private static Map<State, Map<TokenType, Rule>> rules = new HashMap<>();
    static {
        for (State state : State.values()) {
            rules.put(state, new HashMap<>());
        }

        rules.get(State.NOT_INITIALIZED).put(TokenType.ROOT, new Rule(State.PARSING, (t, path) -> {}));

        rules.get(State.PARSING).put(TokenType.DOT, new Rule(State.DOT_ACQUIRED, (t, path) -> {}));
        rules.get(State.DOT_ACQUIRED).put(TokenType.OBJECT_SEGMENT, new Rule(State.PARSING, (t, path) -> path.add(new JsonPathFieldSegmentMatcher(t.getValue()))));

        rules.get(State.PARSING).put(TokenType.ARRAY_START, new Rule(State.ARRAY_STARTED, (t, path) -> {}));
        rules.get(State.ARRAY_STARTED).put(TokenType.WILDCARD, new Rule(State.ARRAY_INDEX_ACQUIRED, (t, path) -> path.add(JsonPathArraySegmentAnyMatcher.INSTANCE)));
        rules.get(State.ARRAY_STARTED).put(TokenType.ARRAY_INDEX, new Rule(State.ARRAY_INDEX_ACQUIRED, (t, path) -> path.add(new JsonPathArraySegmentMatcher(Integer.valueOf(t.getValue())))));
        rules.get(State.ARRAY_INDEX_ACQUIRED).put(TokenType.ARRAY_END, new Rule(State.PARSING, (t, path) -> {}));
    }


    private final LinkedList<JsonPathSegmentMatcher> segments = new LinkedList<>();


    private State state = State.NOT_INITIALIZED;

    public void accept(Token token) {
        Map<TokenType, Rule> ruleMap = rules.get(state);
        Rule rule = ruleMap.get(token.getType());
        if (Objects.isNull(rule)) {
            throw getError();
        }

        rule.action.accept(token, segments);
        state = rule.after;
    }

    public LinkedList<JsonPathSegmentMatcher> finish() {
        if (!finiteStates.contains(state)) {
            throw getError();
        }

        return segments;
    }

    private IllegalStateException getError() {
        String list = rules.get(state).keySet().stream()
                .map(tk -> tk.pattern)
                .collect(Collectors.joining(", "));
        return new IllegalStateException(String.format("Expecting one of %s", list));
    }
}

