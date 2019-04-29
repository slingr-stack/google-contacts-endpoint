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
public class Website extends Field<com.google.gdata.data.contacts.Website> {
    public static final String LABEL = "website";
    public static final String LIST_LABEL = "websites";

    private String href;
    private String label;
    private String rel;
    private boolean primary = false;

    public Website(String href, String label, String rel, Boolean primary) {
        this.href = string(href);
        this.label = string(label);
        this.rel = string(rel);
        this.primary = bool(primary, false);
    }

    public static Website from(com.google.gdata.data.contacts.Website o) {
        if(o == null){
            return null;
        }
        String rel = null;
        if(o.hasRel()) {
            rel = o.getRel().toValue();
        }
        return new Website(o.getHref(), o.getLabel(), rel, o.getPrimary());
    }

    public static Website from(Json j) {
        if(j == null){
            return null;
        }
        return new Website(j.string("href"), j.string("label"), j.string("rel"), j.bool("primary"));
    }

    public static List<Website> from(Collection l) {
        List<Website> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                Website website = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        website = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.contacts.Website){
                        website = from((com.google.gdata.data.contacts.Website) o);
                    }
                } finally {
                    if(website != null){
                        list.add(website);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.contacts.Website toObject(){
        com.google.gdata.data.contacts.Website.Rel relObj = null;
        if(StringUtils.isNotBlank(rel)) {
            for (com.google.gdata.data.contacts.Website.Rel r : com.google.gdata.data.contacts.Website.Rel.values()) {
                if (rel.equalsIgnoreCase(r.toValue())){
                    relObj = r;
                    break;
                }
            }
        }

        return new com.google.gdata.data.contacts.Website(href, label, primary, relObj);
    }

    @Override
    public Json toJson(){
        Json j = Json.map()
                .setIfNotEmpty("href", href)
                .setIfNotEmpty("label", label)
                .setIfNotEmpty("rel", rel);

        if(primary){
            j.set("primary", true);
        }
        return j;
    }
}
