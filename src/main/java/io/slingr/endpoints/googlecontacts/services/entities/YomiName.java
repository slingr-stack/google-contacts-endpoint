package io.slingr.endpoints.googlecontacts.services.entities;

import io.slingr.endpoints.utils.Json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Created by lefunes on 19/06/15.
 */
public class YomiName extends SimpleField {
    public static final String LABEL = "yomiName";
    public static final String LIST_LABEL = "yomiNames";

    public YomiName(String value) {
        super(value);
    }

    public static YomiName from(com.google.gdata.data.contacts.YomiName o) {
        if(o == null){
            return null;
        }
        return new YomiName(o.getValue());
    }

    public static YomiName from(Json j) {
        if(j == null){
            return null;
        }
        return new YomiName(j.string(LABEL));
    }

    public static List<YomiName> from(Collection l) {
        List<YomiName> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                YomiName yomiName = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        yomiName = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.contacts.YomiName){
                        yomiName = from((com.google.gdata.data.contacts.YomiName) o);
                    }
                } finally {
                    if(yomiName != null){
                        list.add(yomiName);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.contacts.YomiName toObject() {
        return new com.google.gdata.data.contacts.YomiName(value);
    }

    @Override
    public void addToJson(Json json) {
        if(json != null){
            json.set(LABEL, value);
        }
    }
}
