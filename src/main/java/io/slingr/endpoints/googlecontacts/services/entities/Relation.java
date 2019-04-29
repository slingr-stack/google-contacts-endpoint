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
public class Relation extends Field<com.google.gdata.data.contacts.Relation> {
    public static final String LABEL = "relation";
    public static final String LIST_LABEL = "relations";

    private String label;
    private String rel;
    private String value;

    public Relation(String label, String rel, String value) {
        this.label = string(label);
        this.rel = string(rel);
        this.value = string(value);
    }

    public static Relation from(com.google.gdata.data.contacts.Relation o) {
        if(o == null){
            return null;
        }
        String rel = null;
        if(o.hasRel()) {
            rel = o.getRel().toValue();
        }
        return new Relation(o.getLabel(), rel, o.getValue());
    }

    public static Relation from(Json j) {
        if(j == null){
            return null;
        }
        return new Relation(j.string("label"), j.string("rel"), j.string("value"));
    }

    public static List<Relation> from(Collection l) {
        List<Relation> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                Relation relation = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        relation = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.contacts.Relation){
                        relation = from((com.google.gdata.data.contacts.Relation) o);
                    }
                } finally {
                    if(relation != null){
                        list.add(relation);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.contacts.Relation toObject(){
        com.google.gdata.data.contacts.Relation.Rel relObj = null;
        if(StringUtils.isNotBlank(rel)) {
            for (com.google.gdata.data.contacts.Relation.Rel r : com.google.gdata.data.contacts.Relation.Rel.values()) {
                if (rel.equalsIgnoreCase(r.toValue())){
                    relObj = r;
                    break;
                }
            }
        }

        return new com.google.gdata.data.contacts.Relation(label, relObj, value);
    }

    @Override
    public Json toJson(){
        return Json.map()
                .setIfNotEmpty("label", label)
                .setIfNotEmpty("rel", rel)
                .setIfNotEmpty("value", value);
    }
}
