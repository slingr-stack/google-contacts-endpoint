package io.slingr.endpoints.googlecontacts.services.entities;

import io.slingr.endpoints.utils.Json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Created by lefunes on 19/06/15.
 */
public class UserDefinedField extends Field<com.google.gdata.data.contacts.UserDefinedField> {
    public static final String LABEL = "userDefinedField";
    public static final String LIST_LABEL = "userDefinedFields";

    private String key;
    private String value;

    public UserDefinedField(String key, String value) {
        this.key = string(key);
        this.value = string(value);
    }

    public static UserDefinedField from(com.google.gdata.data.contacts.UserDefinedField o) {
        if(o == null){
            return null;
        }
        return new UserDefinedField(o.getKey(), o.getValue());
    }

    public static UserDefinedField from(Json j) {
        if(j == null){
            return null;
        }
        return new UserDefinedField(j.string("key"), j.string("value"));
    }

    public static List<UserDefinedField> from(Collection l) {
        List<UserDefinedField> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                UserDefinedField userDefinedField = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        userDefinedField = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.contacts.UserDefinedField){
                        userDefinedField = from((com.google.gdata.data.contacts.UserDefinedField) o);
                    }
                } finally {
                    if(userDefinedField != null){
                        list.add(userDefinedField);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.contacts.UserDefinedField toObject(){
        return new com.google.gdata.data.contacts.UserDefinedField(key, value);
    }

    @Override
    public Json toJson(){
        return Json.map()
                .setIfNotEmpty("key", key)
                .setIfNotEmpty("value", value);
    }
}
