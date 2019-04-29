package io.slingr.endpoints.googlecontacts.services.entities;

import io.slingr.endpoints.utils.Json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Created by lefunes on 19/06/15.
 */
public class Nickname extends SimpleField<com.google.gdata.data.contacts.Nickname> {
    public static final String LABEL = "nickname";
    public static final String LIST_LABEL = "nicknames";

    public Nickname(String value) {
        super(value);
    }

    public static Nickname from(com.google.gdata.data.contacts.Nickname o) {
        if(o == null){
            return null;
        }
        return new Nickname(o.getValue());
    }

    public static Nickname from(Json j) {
        if(j == null){
            return null;
        }
        return new Nickname(j.string(LABEL));
    }

    public static List<Nickname> from(Collection l) {
        List<Nickname> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                Nickname nickname = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        nickname = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.contacts.Nickname){
                        nickname = from((com.google.gdata.data.contacts.Nickname) o);
                    }
                } finally {
                    if(nickname != null){
                        list.add(nickname);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.contacts.Nickname toObject() {
        return new com.google.gdata.data.contacts.Nickname(value);
    }

    @Override
    public void addToJson(Json json) {
        if(json != null){
            json.set(LABEL, value);
        }
    }
}
