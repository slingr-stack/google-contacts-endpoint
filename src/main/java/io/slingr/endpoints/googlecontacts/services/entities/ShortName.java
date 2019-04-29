package io.slingr.endpoints.googlecontacts.services.entities;

import io.slingr.endpoints.utils.Json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Created by lefunes on 19/06/15.
 */
public class ShortName extends SimpleField<com.google.gdata.data.contacts.ShortName> {
    public static final String LABEL = "shortName";
    public static final String LIST_LABEL = "shortNames";

    public ShortName(String value) {
        super(value);
    }

    public static ShortName from(com.google.gdata.data.contacts.ShortName o) {
        if(o == null){
            return null;
        }
        return new ShortName(o.getValue());
    }

    public static ShortName from(Json j) {
        if(j == null){
            return null;
        }
        return new ShortName(j.string(LABEL));
    }

    public static List<ShortName> from(Collection l) {
        List<ShortName> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                ShortName shortName = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        shortName = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.contacts.ShortName){
                        shortName = from((com.google.gdata.data.contacts.ShortName) o);
                    }
                } finally {
                    if(shortName != null){
                        list.add(shortName);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.contacts.ShortName toObject() {
        return new com.google.gdata.data.contacts.ShortName(value);
    }

    @Override
    public void addToJson(Json json) {
        if(json != null){
            json.set(LABEL, value);
        }
    }
}
