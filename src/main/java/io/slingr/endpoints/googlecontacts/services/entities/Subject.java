package io.slingr.endpoints.googlecontacts.services.entities;

import io.slingr.endpoints.utils.Json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Created by lefunes on 19/06/15.
 */
public class Subject extends SimpleField {
    public static final String LABEL = "subject";
    public static final String LIST_LABEL = "subjects";

    public Subject(String value) {
        super(value);
    }

    public static Subject from(com.google.gdata.data.contacts.Subject o) {
        if(o == null){
            return null;
        }
        return new Subject(o.getValue());
    }

    public static Subject from(Json j) {
        if(j == null){
            return null;
        }
        return new Subject(j.string(LABEL));
    }

    public static List<Subject> from(Collection l) {
        List<Subject> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                Subject subject = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        subject = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.contacts.Subject){
                        subject = from((com.google.gdata.data.contacts.Subject) o);
                    }
                } finally {
                    if(subject != null){
                        list.add(subject);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.contacts.Subject toObject() {
        return new com.google.gdata.data.contacts.Subject(value);
    }

    @Override
    public void addToJson(Json json) {
        if(json != null){
            json.set(LABEL, value);
        }
    }
}
