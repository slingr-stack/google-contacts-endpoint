package io.slingr.endpoints.googlecontacts.services.entities;

import io.slingr.endpoints.utils.Json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Created by lefunes on 19/06/15.
 */
public class Mileage extends SimpleField<com.google.gdata.data.contacts.Mileage> {
    public static final String LABEL = "mileage";
    public static final String LIST_LABEL = "mileages";

    public Mileage(String value) {
        super(value);
    }

    public static Mileage from(com.google.gdata.data.contacts.Mileage o) {
        if(o == null){
            return null;
        }
        return new Mileage(o.getValue());
    }

    public static Mileage from(Json j) {
        if(j == null){
            return null;
        }
        return new Mileage(j.string(LABEL));
    }

    public static List<Mileage> from(Collection l) {
        List<Mileage> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                Mileage mileage = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        mileage = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.contacts.Mileage){
                        mileage = from((com.google.gdata.data.contacts.Mileage) o);
                    }
                } finally {
                    if(mileage != null){
                        list.add(mileage);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.contacts.Mileage toObject() {
        return new com.google.gdata.data.contacts.Mileage(value);
    }

    @Override
    public void addToJson(Json json) {
        if(json != null){
            json.set(LABEL, value);
        }
    }
}
