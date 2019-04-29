package io.slingr.endpoints.googlecontacts.services.entities;

import com.google.gdata.data.extensions.*;
import io.slingr.endpoints.utils.Json;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Created by lefunes on 19/06/15.
 */
public class Name extends Field<com.google.gdata.data.extensions.Name> {
    public static final String LABEL = "name";
    public static final String LIST_LABEL = "names";

    private String fullName;
    private String fullNameYomi;
    private String givenName;
    private String givenNameYomi;
    private String additionalName;
    private String additionalNameYomi;
    private String familyName;
    private String familyNameYomi;
    private String prefix;
    private String suffix;

    public Name(String fullName, String givenName, String additionalName, String familyName) {
        this(fullName, givenName, additionalName, familyName, null, null);
    }

    public Name(String fullName, String givenName, String additionalName, String familyName, String prefix, String suffix) {
        this(fullName, null, givenName, null, additionalName, null, familyName, null, prefix, suffix);
    }

    public Name(String fullName, String fullNameYomi, String givenName, String givenNameYomi, String additionalName, String additionalNameYomi, String familyName, String familyNameYomi, String prefix, String suffix) {
        this.fullName = string(fullName);
        this.fullNameYomi = string(fullNameYomi);
        this.givenName = string(givenName);
        this.givenNameYomi = string(givenNameYomi);
        this.additionalName = string(additionalName);
        this.additionalNameYomi = string(additionalNameYomi);
        this.familyName = string(familyName);
        this.familyNameYomi = string(familyNameYomi);
        this.prefix = string(prefix);
        this.suffix = string(suffix);
    }

    public static Name from(com.google.gdata.data.extensions.Name o) {
        if(o == null){
            return null;
        }
        String fullName = null;
        String fullNameYomi = null;
        if(o.hasFullName()){
            fullName = o.getFullName().getValue();
            fullNameYomi = o.getFullName().getYomi();
        }
        String givenName = null;
        String givenNameYomi = null;
        if(o.hasGivenName()){
            givenName = o.getGivenName().getValue();
            givenNameYomi = o.getGivenName().getYomi();
        }
        String additionalName = null;
        String additionalNameYomi = null;
        if(o.hasAdditionalName()){
            additionalName = o.getAdditionalName().getValue();
            additionalNameYomi = o.getAdditionalName().getYomi();
        }
        String familyName = null;
        String familyNameYomi = null;
        if(o.hasFamilyName()){
            familyName = o.getFamilyName().getValue();
            familyNameYomi = o.getFamilyName().getYomi();
        }
        String prefix = null;
        if(o.hasNamePrefix()){
            prefix = o.getNamePrefix().getValue();
        }
        String suffix = null;
        if(o.hasNameSuffix()){
            suffix = o.getNameSuffix().getValue();
        }

        return new Name(fullName, fullNameYomi, givenName, givenNameYomi, additionalName, additionalNameYomi, familyName, familyNameYomi, prefix, suffix );
    }

    public static Name from(Json j) {
        if(j == null){
            return null;
        }
        return new Name(j.string("fullName"), j.string("fullNameYomi"), j.string("givenName"),  j.string("givenNameYomi"), j.string("additionalName"), j.string("additionalNameYomi"), j.string("familyName"), j.string("familyNameYomi"), j.string("prefix"), j.string("suffix"));
    }

    public static List<Name> from(Collection l) {
        List<Name> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                Name name = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        name = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.extensions.Name){
                        name = from((com.google.gdata.data.extensions.Name) o);
                    }
                } finally {
                    if(name != null){
                        list.add(name);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.extensions.Name toObject(){
        final com.google.gdata.data.extensions.Name name = new com.google.gdata.data.extensions.Name();
        if(StringUtils.isNotBlank(fullName)) {
            name.setFullName(new FullName(fullName, fullNameYomi));
        }
        if(StringUtils.isNotBlank(givenName)) {
            name.setGivenName(new GivenName(givenName, givenNameYomi));
        }
        if(StringUtils.isNotBlank(additionalName)) {
            name.setAdditionalName(new AdditionalName(additionalName, additionalNameYomi));
        }
        if(StringUtils.isNotBlank(familyName)) {
            name.setFamilyName(new FamilyName(familyName, familyNameYomi));
        }
        if(StringUtils.isNotBlank(prefix)) {
            name.setNamePrefix(new NamePrefix(prefix));
        }
        if(StringUtils.isNotBlank(suffix)) {
            name.setNameSuffix(new NameSuffix(suffix));
        }

        return name;
    }

    @Override
    public Json toJson(){
        return Json.map()
                .setIfNotEmpty("fullName", fullName)
                .setIfNotEmpty("fullNameYomi", fullNameYomi)
                .setIfNotEmpty("givenName", givenName)
                .setIfNotEmpty("givenNameYomi", givenNameYomi)
                .setIfNotEmpty("additionalName", additionalName)
                .setIfNotEmpty("additionalNameYomi", additionalNameYomi)
                .setIfNotEmpty("familyName", familyName)
                .setIfNotEmpty("familyNameYomi", familyNameYomi)
                .setIfNotEmpty("prefix", prefix)
                .setIfNotEmpty("suffix", suffix);
    }
}
