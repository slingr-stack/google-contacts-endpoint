package io.slingr.endpoints.googlecontacts.services.entities;

import io.slingr.endpoints.utils.Json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Created by lefunes on 19/06/15.
 */
public class Status extends Field<com.google.gdata.data.contacts.Status> {
    public static final String LABEL = "status";
    public static final String LIST_LABEL = "statusList";

    private String value;
    private Boolean indexed = false;

    public Status(String value, boolean indexed) {
        this.value = string(value);
        this.indexed = bool(indexed, false);
    }

    public static Status from(com.google.gdata.data.contacts.Status o) {
        if(o == null){
            return null;
        }
        return new Status(o.toString(), o.getIndexed());
    }

    public static Status from(Json j) {
        if(j == null){
            return null;
        }
        return new Status(j.string("value"), j.bool("indexed"));
    }

    public static List<Status> from(Collection l) {
        List<Status> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                Status status = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        status = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.contacts.Status){
                        status = from((com.google.gdata.data.contacts.Status) o);
                    }
                } finally {
                    if(status != null){
                        list.add(status);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.contacts.Status toObject(){
        return new com.google.gdata.data.contacts.Status(indexed);
    }

    @Override
    public Json toJson(){
        Json j = Json.map()
                .setIfNotEmpty("value", value);

        if(indexed != null && indexed){
            j.set("indexed", true);
        }
        return j;
    }
}
