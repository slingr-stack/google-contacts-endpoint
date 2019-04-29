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
public class Hobby extends SimpleField<com.google.gdata.data.contacts.Hobby> {
    public static final String LABEL = "hobby";
    public static final String LIST_LABEL = "hobbies";

    public Hobby(String value) {
        super(value);
    }

    public static Hobby from(com.google.gdata.data.contacts.Hobby o) {
        if(o == null){
            return null;
        }
        return new Hobby(o.getValue());
    }

    public static Hobby from(Json j) {
        if(j == null){
            return null;
        }
        return from(j.string(LABEL));
    }

    public static Hobby from(String h) {
        if(StringUtils.isBlank(h)){
            return null;
        }
        return new Hobby(h);
    }

    public static List<Hobby> from(Collection l) {
        List<Hobby> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                Hobby hobby = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        hobby = from(Json.fromObject(o));
                    } else if(o instanceof String){
                        hobby = from(o.toString());
                    } else if (o instanceof com.google.gdata.data.contacts.Hobby){
                        hobby = from((com.google.gdata.data.contacts.Hobby) o);
                    }
                } finally {
                    if(hobby != null){
                        list.add(hobby);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.contacts.Hobby toObject() {
        return new com.google.gdata.data.contacts.Hobby(value);
    }

    @Override
    public void addToJson(Json json) {
        if(json != null){
            json.set(LABEL, value);
        }
    }

    public String toString(){
        return value;
    }
}
