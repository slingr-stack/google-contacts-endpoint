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
public class Jot extends Field<com.google.gdata.data.contacts.Jot> {
    public static final String LABEL = "jot";
    public static final String LIST_LABEL = "jots";

    private String rel;
    private String value;

    public Jot(String rel, String value) {
        this.rel = string(rel);
        this.value = string(value);
    }

    public static Jot from(com.google.gdata.data.contacts.Jot o) {
        if(o == null){
            return null;
        }
        String rel = null;
        if(o.hasRel()) {
            rel = o.getRel().toString();
        }
        return new Jot(rel, o.getValue());
    }

    public static Jot from(Json j) {
        if(j == null){
            return null;
        }
        return new Jot(j.string("rel"), j.string("value"));
    }

    public static List<Jot> from(Collection l) {
        List<Jot> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                Jot jot = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        jot = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.contacts.Jot){
                        jot = from((com.google.gdata.data.contacts.Jot) o);
                    }
                } finally {
                    if(jot != null){
                        list.add(jot);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.contacts.Jot toObject(){
        com.google.gdata.data.contacts.Jot.Rel relObj = null;
        if(StringUtils.isNotBlank(rel)) {
            for (com.google.gdata.data.contacts.Jot.Rel r : com.google.gdata.data.contacts.Jot.Rel.values()) {
                if (rel.equalsIgnoreCase(r.toString())){
                    relObj = r;
                    break;
                }
            }
        }

        return new com.google.gdata.data.contacts.Jot(relObj, value);
    }

    @Override
    public Json toJson(){
        return Json.map()
                .setIfNotEmpty("rel", rel)
                .setIfNotEmpty("value", value);
    }
}
