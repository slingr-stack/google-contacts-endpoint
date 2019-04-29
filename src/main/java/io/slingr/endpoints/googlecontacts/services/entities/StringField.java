package io.slingr.endpoints.googlecontacts.services.entities;

import io.slingr.endpoints.utils.Json;

/**
 * <p>Created by lefunes on 19/06/15.
 */
public abstract class StringField {

    protected String value;

    public StringField(String value) {
        this.value = string(value);
    }

    public static String string(String value){
        return Field.string(value);
    }

    public static String string(String value, String defaultValue){
        return Field.string(value, defaultValue);
    }

    public abstract String toObject();
    public abstract void addToJson(Json json);
}
