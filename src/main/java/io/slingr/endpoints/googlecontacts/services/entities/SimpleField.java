package io.slingr.endpoints.googlecontacts.services.entities;

import com.google.gdata.data.Extension;
import io.slingr.endpoints.utils.Json;

/**
 * <p>Created by lefunes on 19/06/15.
 */
public abstract class SimpleField<T extends Extension> {

    protected String value;

    public SimpleField(String value) {
        this.value = string(value);
    }

    public static String string(String value){
        return Field.string(value);
    }

    public static String string(String value, String defaultValue){
        return Field.string(value, defaultValue);
    }

    public abstract T toObject();
    public abstract void addToJson(Json json);
}
