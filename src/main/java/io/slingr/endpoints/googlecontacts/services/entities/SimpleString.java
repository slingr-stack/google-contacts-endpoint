package io.slingr.endpoints.googlecontacts.services.entities;

import io.slingr.endpoints.utils.Json;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Created by lefunes on 19/06/15.
 */
public class SimpleString extends StringField {

    private final String key;

    public SimpleString(String key, String value) {
        super(value);
        this.key = key;
    }

    public static void add(String key, String value, Json json) {
        final SimpleString tag = from(key, value);
        if(tag != null){
            tag.addToJson(json);
        }
    }

    public static SimpleString from(String key, String value) {
        if(StringUtils.isBlank(value) || StringUtils.isBlank(key)){
            return null;
        }
        return new SimpleString(key, value);
    }

    public static SimpleString from(String key, Json j) {
        if(j == null || StringUtils.isBlank(key)){
            return null;
        }
        return new SimpleString(key, j.string(key));
    }

    public static List<SimpleString> from(String key, Collection l) {
        List<SimpleString> list = new ArrayList<>();
        if(StringUtils.isNotBlank(key) && l != null && !l.isEmpty()){
            for (Object o : l) {
                SimpleString simpleString = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        simpleString = from(key, Json.fromObject(o));
                    } else {
                        simpleString = from(key, o.toString());
                    }
                } finally {
                    if(simpleString != null){
                        list.add(simpleString);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public String toObject() {
        return value;
    }

    @Override
    public void addToJson(Json json) {
        if(json != null){
            json.setIfNotEmpty(key, value);
        }
    }
}
