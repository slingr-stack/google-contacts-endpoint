package io.slingr.endpoints.googlecontacts.services.entities;

import io.slingr.endpoints.utils.Json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Created by lefunes on 19/06/15.
 */
public class Event extends Field<com.google.gdata.data.contacts.Event> {
    public static final String LABEL = "event";
    public static final String LIST_LABEL = "events";

    private String label;
    private String rel;
    private When when;

    public Event(String label, String rel, When when) {
        this.label = string(label);
        this.rel = string(rel);
        this.when = when;
    }

    public static Event from(com.google.gdata.data.contacts.Event o) {
        if(o == null){
            return null;
        }
        When when = null;
        if(o.hasWhen()){
            when = When.from(o.getWhen());
        }
        return new Event(o.getLabel(), o.getRel(), when);
    }

    public static Event from(Json j) {
        if(j == null){
            return null;
        }
        When when = null;
        Json jWhen = j.json(When.LABEL);
        if(jWhen != null){
            when = When.from(jWhen);
        }
        return new Event(j.string("label"), j.string("rel"),  when);
    }

    public static List<Event> from(Collection l) {
        List<Event> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                Event event = null;
                try {
                    if (o instanceof Json || o instanceof Map) {
                        event = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.contacts.Event) {
                        event = from((com.google.gdata.data.contacts.Event) o);
                    }
                } finally {
                    if(event != null){
                        list.add(event);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.contacts.Event toObject(){
        final com.google.gdata.data.contacts.Event event = new com.google.gdata.data.contacts.Event(label, rel);

        if(when != null){
            event.setWhen(when.toObject());
        }

        return event;
    }

    @Override
    public Json toJson(){
        Json json = Json.map()
                .setIfNotEmpty("label", label)
                .setIfNotEmpty("rel", rel);
        if(when != null){
            json.setIfNotEmpty(When.LABEL, when.toJson());
        }
        return json;
    }
}
