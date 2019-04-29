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
public class Where extends Field<com.google.gdata.data.extensions.Where> {
    public static final String LABEL = "where";
    public static final String LIST_LABEL = "whereList";

    private String rel;
    private String label;
    private String valueString;

    public Where(String rel, String label, String valueString) {
        this.rel = string(rel);
        this.label = string(label);
        this.valueString = string(valueString);
    }

    public static Where from(com.google.gdata.data.extensions.Where o) {
        if(o == null){
            return null;
        }
        return new Where(o.getRel(), o.getLabel(), o.getValueString());
    }

    public static Where from(Json j) {
        if(j == null){
            return null;
        }
        return new Where(j.string("rel"), j.string("label"), j.string("valueString"));
    }

    public static List<Where> from(Collection l) {
        List<Where> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                Where where = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        where = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.extensions.Where){
                        where = from((com.google.gdata.data.extensions.Where) o);
                    }
                } finally {
                    if(where != null){
                        list.add(where);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.extensions.Where toObject(){
        final com.google.gdata.data.extensions.Where o = new com.google.gdata.data.extensions.Where(rel, label, valueString);

        if(StringUtils.isBlank(o.getRel())) {
            if (StringUtils.isBlank(o.getLabel())) {
                o.setLabel("event");
            }
        } else {
            switch (o.getRel().toLowerCase()){
                case "event.alternate":
                    o.setRel(com.google.gdata.data.extensions.Where.Rel.EVENT_ALTERNATE);
                    break;
                case "event.parking":
                    o.setRel(com.google.gdata.data.extensions.Where.Rel.EVENT_PARKING);
                    break;
            }
        }

        return o;
    }

    @Override
    public Json toJson(){
        return Json.map()
                .setIfNotEmpty("rel", rel)
                .setIfNotEmpty("label", label)
                .setIfNotEmpty("valueString", valueString);
    }
}
