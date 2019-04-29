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
public class Gender extends SimpleField<com.google.gdata.data.contacts.Gender> {
    public static final String LABEL = "gender";
    public static final String LIST_LABEL = "genders";

    public Gender(String value) {
        super(value);
    }

    public static Gender from(com.google.gdata.data.contacts.Gender o) {
        if(o == null){
            return null;
        }
        String value = null;
        if(o.hasValue()){
            value = o.getValue().toString();
        }
        return new Gender(value);
    }

    public static Gender from(Json j) {
        if(j == null){
            return null;
        }
        return new Gender(j.string(LABEL));
    }

    public static List<Gender> from(Collection l) {
        List<Gender> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                Gender gender = null;
                try {
                    if (o instanceof Json || o instanceof Map) {
                        gender = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.contacts.Gender) {
                        gender = from((com.google.gdata.data.contacts.Gender) o);
                    }
                } finally {
                    if(gender != null){
                        list.add(gender);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.contacts.Gender toObject() {
        com.google.gdata.data.contacts.Gender.Value valueObj = null;
        if(StringUtils.isNotBlank(value)) {
            for (com.google.gdata.data.contacts.Gender.Value v : com.google.gdata.data.contacts.Gender.Value.values()) {
                if (value.equalsIgnoreCase(v.toString())){
                    valueObj = v;
                    break;
                }
            }
        }
        return new com.google.gdata.data.contacts.Gender(valueObj);
    }

    @Override
    public void addToJson(Json json) {
        if(json != null){
            json.set(LABEL, value);
        }
    }
}
