package io.slingr.endpoints.googlecontacts.services.entities;

import com.google.gdata.data.DateTime;
import io.slingr.endpoints.utils.Json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Created by lefunes on 19/06/15.
 */
public class When extends Field<com.google.gdata.data.extensions.When> {
    public static final String LABEL = "when";
    public static final String LIST_LABEL = "whenList";

    private DateTime startTime;
    private DateTime endTime;
    private String valueString;
    private String rel;

    public When(DateTime startTime, DateTime endTime, String valueString, String rel) {
        this.startTime = dateTime(startTime);
        this.endTime = dateTime(endTime);
        this.valueString = string(valueString);
        this.rel = string(rel);
    }

    public static When from(com.google.gdata.data.extensions.When o) {
        if(o == null){
            return null;
        }
        return new When(o.getStartTime(), o.getEndTime(), o.getValueString(), o.getRel());
    }

    public static When from(Json j) {
        if(j == null){
            return null;
        }
        return new When(dateTime(j.string("startTime")), dateTime(j.string("endTime")), j.string("valueString"), j.string("rel"));
    }

    public static List<When> from(Collection l) {
        List<When> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                When when = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        when = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.extensions.When){
                        when = from((com.google.gdata.data.extensions.When) o);
                    }
                } finally {
                    if(when != null){
                        list.add(when);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.extensions.When toObject(){
        final com.google.gdata.data.extensions.When when = new com.google.gdata.data.extensions.When();
        when.setStartTime(startTime);
        when.setEndTime(endTime);
        when.setValueString(valueString);
        when.setRel(rel);

        return when;
    }

    @Override
    public Json toJson(){
        return Json.map()
                .setIfNotEmpty("startTime", dateTimeString(startTime))
                .setIfNotEmpty("endTime", dateTimeString(endTime))
                .setIfNotEmpty("valueString", valueString)
                .setIfNotEmpty("rel", rel);
    }
}
