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
public class Category extends ComplexField<com.google.gdata.data.Category> {
    public static final String LABEL = "category";
    public static final String LIST_LABEL = "categories";

    private String label;
    private String labelLang;
    private String scheme;
    private String term;

    public Category(String label, String labelLang, String scheme, String term) {
        this.label = string(label);
        this.labelLang = string(labelLang);
        this.scheme = string(scheme);
        this.term = string(term);
    }

    public static Category from(com.google.gdata.data.Category o) {
        if(o == null){
            return null;
        }
        return new Category(o.getLabel(), o.getLabelLang(), o.getScheme(), o.getTerm());
    }

    public static Category from(Json j) {
        if(j == null){
            return null;
        }
        return new Category(j.string("label"), j.string("labelLang"), j.string("scheme"), j.string("term"));
    }

    public static List<Category> from(Collection l) {
        List<Category> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                Category category = null;
                try {
                    if (o instanceof Json || o instanceof Map) {
                        category = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.Category) {
                        category = from((com.google.gdata.data.Category) o);
                    }
                } finally {
                    if(category != null){
                        list.add(category);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.Category toObject(){
        final com.google.gdata.data.Category c = new com.google.gdata.data.Category(scheme, term, label);
        if(StringUtils.isNotBlank(labelLang)){
            c.setLabelLang(labelLang);
        }
        return c;
    }

    @Override
    public Json toJson(){
        return Json.map()
                .setIfNotEmpty("label", label)
                .setIfNotEmpty("labelLang", labelLang)
                .setIfNotEmpty("scheme", scheme)
                .setIfNotEmpty("term", term);
    }
}
