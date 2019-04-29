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
public class CalendarLink extends Field<com.google.gdata.data.contacts.CalendarLink> {
    public static final String LABEL = "calendarLink";
    public static final String LIST_LABEL = "calendarLinks";

    private String href;
    private String label;
    private String rel;
    private boolean primary = false;

    public CalendarLink(String href, String label, String rel, Boolean primary) {
        this.href = string(href);
        this.label = string(label);
        this.rel = string(rel);
        this.primary = bool(primary, false);
    }

    public static CalendarLink from(com.google.gdata.data.contacts.CalendarLink o) {
        if(o == null){
            return null;
        }
        String rel = null;
        if(o.hasRel()) {
            rel = o.getRel().toValue();
        }
        return new CalendarLink(o.getHref(), o.getLabel(), rel, o.getPrimary());
    }

    public static CalendarLink from(Json j) {
        if(j == null){
            return null;
        }
        return new CalendarLink(j.string("href"), j.string("label"), j.string("rel"), j.bool("primary"));
    }

    public static List<CalendarLink> from(Collection l) {
        List<CalendarLink> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                CalendarLink calendarLink = null;
                try {
                    if (o instanceof Json || o instanceof Map) {
                        calendarLink = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.contacts.CalendarLink) {
                        calendarLink = from((com.google.gdata.data.contacts.CalendarLink) o);
                    }
                } finally {
                    if(calendarLink != null){
                        list.add(calendarLink);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.contacts.CalendarLink toObject(){
        com.google.gdata.data.contacts.CalendarLink.Rel relObj = null;
        if(StringUtils.isNotBlank(rel)) {
            for (com.google.gdata.data.contacts.CalendarLink.Rel r : com.google.gdata.data.contacts.CalendarLink.Rel.values()) {
                if (rel.equalsIgnoreCase(r.toValue())){
                    relObj = r;
                    break;
                }
            }
        }

        return new com.google.gdata.data.contacts.CalendarLink(href, label, primary, relObj);
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
