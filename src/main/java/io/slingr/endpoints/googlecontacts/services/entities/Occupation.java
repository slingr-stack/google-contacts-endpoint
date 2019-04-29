package io.slingr.endpoints.googlecontacts.services.entities;

import io.slingr.endpoints.utils.Json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Created by lefunes on 19/06/15.
 */
public class Occupation extends SimpleField<com.google.gdata.data.contacts.Occupation> {
    public static final String LABEL = "occupation";
    public static final String LIST_LABEL = "occupations";

    public Occupation(String value) {
        super(value);
    }

    public static Occupation from(com.google.gdata.data.contacts.Occupation o) {
        if(o == null){
            return null;
        }
        return new Occupation(o.getValue());
    }

    public static Occupation from(Json j) {
        if(j == null){
            return null;
        }
        return new Occupation(j.string(LABEL));
    }

    public static List<Occupation> from(Collection l) {
        List<Occupation> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                Occupation occupation = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        occupation = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.contacts.Occupation){
                        occupation = from((com.google.gdata.data.contacts.Occupation) o);
                    }
                } finally {
                    if(occupation != null){
                        list.add(occupation);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.contacts.Occupation toObject() {
        return new com.google.gdata.data.contacts.Occupation(value);
    }

    @Override
    public void addToJson(Json json) {
        if(json != null){
            json.set(LABEL, value);
        }
    }
}
