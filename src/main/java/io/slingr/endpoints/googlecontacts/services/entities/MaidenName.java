package io.slingr.endpoints.googlecontacts.services.entities;

import io.slingr.endpoints.utils.Json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Created by lefunes on 19/06/15.
 */
public class MaidenName extends SimpleField<com.google.gdata.data.contacts.MaidenName> {
    public static final String LABEL = "maidenName";
    public static final String LIST_LABEL = "maidenNames";

    public MaidenName(String value) {
        super(value);
    }

    public static MaidenName from(com.google.gdata.data.contacts.MaidenName o) {
        if(o == null){
            return null;
        }
        return new MaidenName(o.getValue());
    }

    public static MaidenName from(Json j) {
        if(j == null){
            return null;
        }
        return new MaidenName(j.string(LABEL));
    }

    public static List<MaidenName> from(Collection l) {
        List<MaidenName> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                MaidenName maidenName = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        maidenName = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.contacts.MaidenName){
                        maidenName = from((com.google.gdata.data.contacts.MaidenName) o);
                    }
                } finally {
                    if(maidenName != null){
                        list.add(maidenName);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.contacts.MaidenName toObject() {
        return new com.google.gdata.data.contacts.MaidenName(value);
    }

    @Override
    public void addToJson(Json json) {
        if(json != null){
            json.set(LABEL, value);
        }
    }
}
