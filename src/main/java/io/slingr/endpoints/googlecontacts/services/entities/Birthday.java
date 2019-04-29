package io.slingr.endpoints.googlecontacts.services.entities;

import io.slingr.endpoints.utils.Json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Created by lefunes on 19/06/15.
 */
public class Birthday extends SimpleField<com.google.gdata.data.contacts.Birthday> {
    public static final String LABEL = "birthday";
    public static final String LIST_LABEL = "birthdays";

    public Birthday(String value) {
        super(value);
    }

    public static Birthday from(com.google.gdata.data.contacts.Birthday o) {
        if(o == null){
            return null;
        }
        return new Birthday(o.getValue());
    }

    public static Birthday from(Json j) {
        if(j == null){
            return null;
        }
        return new Birthday(j.string(LABEL));
    }

    public static List<Birthday> from(Collection l) {
        List<Birthday> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                Birthday birthday = null;
                try {
                    if (o instanceof Json || o instanceof Map) {
                        birthday = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.contacts.Birthday){
                        birthday = from((com.google.gdata.data.contacts.Birthday) o);
                    }
                } finally {
                    if(birthday != null){
                        list.add(birthday);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.contacts.Birthday toObject() {
        return new com.google.gdata.data.contacts.Birthday(value);
    }

    @Override
    public void addToJson(Json json) {
        if(json != null){
            json.set(LABEL, value);
        }
    }
}
