package io.slingr.endpoints.googlecontacts.services.entities;

import io.slingr.endpoints.utils.Json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Created by lefunes on 19/06/15.
 */
public class ExternalId extends Field<com.google.gdata.data.contacts.ExternalId> {
    public static final String LABEL = "externalId";
    public static final String LIST_LABEL = "externalIds";

    private String label;
    private String rel;
    private String value;

    public ExternalId(String label, String rel, String value) {
        this.label = string(label);
        this.rel = string(rel);
        this.value = string(value);
    }

    public static ExternalId from(com.google.gdata.data.contacts.ExternalId o) {
        if(o == null){
            return null;
        }
        return new ExternalId(o.getLabel(), o.getRel(), o.getValue());
    }

    public static ExternalId from(Json j) {
        if(j == null){
            return null;
        }
        return new ExternalId(j.string("label"), j.string("rel"), j.string("value"));
    }

    public static List<ExternalId> from(Collection l) {
        List<ExternalId> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                ExternalId externalId = null;
                try {
                    if (o instanceof Json || o instanceof Map) {
                        externalId = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.contacts.ExternalId) {
                        externalId = from((com.google.gdata.data.contacts.ExternalId) o);
                    }
                } finally {
                    if(externalId != null){
                        list.add(externalId);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.contacts.ExternalId toObject(){
        return new com.google.gdata.data.contacts.ExternalId(label, rel, value);
    }

    @Override
    public Json toJson(){
        return Json.map()
                .setIfNotEmpty("label", label)
                .setIfNotEmpty("rel", rel)
                .setIfNotEmpty("value", value);
    }
}
