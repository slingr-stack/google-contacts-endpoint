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
public class Priority extends SimpleField<com.google.gdata.data.contacts.Priority> {
    public static final String LABEL = "priority";
    public static final String LIST_LABEL = "priorities";

    public Priority(String rel) {
        super(rel);
    }

    public static Priority from(com.google.gdata.data.contacts.Priority o) {
        if(o == null){
            return null;
        }
        String rel = null;
        if(o.hasRel()) {
            rel = o.getRel().toString();
        }
        return new Priority(rel);
    }

    public static Priority from(Json j) {
        if(j == null){
            return null;
        }
        return new Priority(j.string(LABEL));
    }

    public static List<Priority> from(Collection l) {
        List<Priority> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                Priority priority = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        priority = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.contacts.Priority){
                        priority = from((com.google.gdata.data.contacts.Priority) o);
                    }
                } finally {
                    if(priority != null){
                        list.add(priority);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.contacts.Priority toObject(){
        com.google.gdata.data.contacts.Priority.Rel relObj = null;
        if(StringUtils.isNotBlank(value)) {
            for (com.google.gdata.data.contacts.Priority.Rel r : com.google.gdata.data.contacts.Priority.Rel.values()) {
                if (value.equalsIgnoreCase(r.toString())){
                    relObj = r;
                    break;
                }
            }
        }

        return new com.google.gdata.data.contacts.Priority(relObj);
    }

    @Override
    public void addToJson(Json json) {
        if(json != null){
            json.set(LABEL, value);
        }
    }
}
