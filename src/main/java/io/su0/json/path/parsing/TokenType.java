package io.su0.json.path.parsing;

public enum TokenType {
    ROOT("\\$", false),
    DOT("\\.", false),
    ARRAY_START("\\[", false),
    ARRAY_END("\\]", false),
    ARRAY_INDEX("d+", true),
    OBJECT_SEGMENT("\\w", true),
    ;

    public final String pattern;
    public final boolean hasValue;
    public final String groupName;

    TokenType(String pattern, boolean hasValue) {
        this.pattern = pattern;
        this.hasValue = hasValue;
        this.groupName = this.name().replace("_", ""); // No underscore ("_") allowed in group name
    }
}
