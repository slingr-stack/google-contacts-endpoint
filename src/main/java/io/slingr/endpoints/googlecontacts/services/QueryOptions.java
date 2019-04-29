package io.slingr.endpoints.googlecontacts.services;

/**
 * <p>Query options
 *
 * <p>Created by lefunes on 18/06/15.
 */
public enum QueryOptions {
    QUERY("query"),
    MAX_RESULTS("maxResults"),
    START_INDEX("startIndex"),
    UPDATED_MIN("updatedMin"),
    GROUP("group"),
    SHOW_DELETED("showDeleted"),
    ;

    private final String value;

    QueryOptions(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
