package io.slingr.endpoints.googlecontacts.services.entities;

import io.slingr.endpoints.utils.Json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Created by lefunes on 19/06/15.
 */
public class Language extends Field<com.google.gdata.data.contacts.Language> {
    public static final String LABEL = "language";
    public static final String LIST_LABEL = "languages";

    private String code;
    private String label;

    public Language(String code, String label) {
        this.code = string(code);
        this.label = string(label);
    }

    public static Language from(com.google.gdata.data.contacts.Language o) {
        if(o == null){
            return null;
        }
        return new Language(o.getCode(), o.getLabel());
    }

    public static Language from(Json j) {
        if(j == null){
            return null;
        }
        return new Language(j.string("code"), j.string("label"));
    }

    public static List<Language> from(Collection l) {
        List<Language> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                Language language = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        language = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.contacts.Language){
                        language = from((com.google.gdata.data.contacts.Language) o);
                    }
                } finally {
                    if(language != null){
                        list.add(language);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.contacts.Language toObject(){
        return new com.google.gdata.data.contacts.Language(code, label);
    }

    @Override
    public Json toJson(){
        return Json.map()
                .setIfNotEmpty("code", code)
                .setIfNotEmpty("label", label);
    }
}
