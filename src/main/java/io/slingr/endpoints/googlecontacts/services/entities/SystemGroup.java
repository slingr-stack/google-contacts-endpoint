package io.slingr.endpoints.googlecontacts.services.entities;

import io.slingr.endpoints.utils.Json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Created by lefunes on 19/06/15.
 */
public class SystemGroup extends SimpleField<com.google.gdata.data.contacts.SystemGroup> {
    public static final String LABEL = "systemGroup";
    public static final String LIST_LABEL = "systemGroups";

    public SystemGroup(String id) {
        super(id);
    }

    public static SystemGroup from(com.google.gdata.data.contacts.SystemGroup o) {
        if (o == null) {
            return null;
        }
        return new SystemGroup(o.getValue());
    }

    public static SystemGroup from(Json j) {
        if (j == null) {
            return null;
        }
        return new SystemGroup(j.string(LABEL));
    }

    public static List<SystemGroup> from(Collection l) {
        List<SystemGroup> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                SystemGroup systemGroup = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        systemGroup = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.contacts.SystemGroup){
                        systemGroup = from((com.google.gdata.data.contacts.SystemGroup) o);
                    }
                } finally {
                    if(systemGroup != null){
                        list.add(systemGroup);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.contacts.SystemGroup toObject() {
        return new com.google.gdata.data.contacts.SystemGroup(value);
    }

    @Override
    public void addToJson(Json json) {
        if (json != null) {
            json.set(LABEL, value);
        }
    }
}