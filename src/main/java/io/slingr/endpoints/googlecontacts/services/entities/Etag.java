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
public class Etag extends StringField {
    public static final String LABEL = "etag";
    public static final String LIST_LABEL = "etags";

    public Etag(String value) {
        super(value);
    }

    public static void add(String etag, Json json) {
        final Etag tag = from(etag);
        if(tag != null){
            tag.addToJson(json);
        }
    }

    public static Etag from(String etag) {
        if(StringUtils.isBlank(etag)){
            return null;
        }
        if(etag.startsWith("\"")) {
            etag = etag.substring(1);
        }
        if(etag.endsWith("\"")){
            etag = etag.substring(0, etag.length()-1);
        }
        return new Etag(etag);
    }

    public static Etag from(Json j) {
        if(j == null){
            return null;
        }
        return new Etag(j.string(LABEL));
    }

    public static List<Etag> from(Collection l) {
        List<Etag> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                Etag etag = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        etag = from(Json.fromObject(o));
                    } else if(o != null && StringUtils.isNotBlank(o.toString())){
                        etag = from(o.toString());
                    }
                } finally {
                    if(etag != null){
                        list.add(etag);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public String toObject() {
        return String.format("\"%s\"", value);
    }

    @Override
    public void addToJson(Json json) {
        if(json != null){
            json.setIfNotEmpty(LABEL, value);
        }
    }
}
