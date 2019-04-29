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
public class Email extends Field<com.google.gdata.data.extensions.Email> {
    public static final String LABEL = "email";
    public static final String LIST_LABEL = "emails";

    private String address;
    private String label;
    private String rel;
    private String displayName;
    private String quota;
    private boolean primary = false;

    public Email(String address, String label, String rel, String displayName, String quota, Boolean primary) {
        this.address = string(address);
        this.label = string(label);
        this.rel = string(rel);
        this.displayName = string(displayName);
        this.quota = string(quota);
        this.primary = bool(primary, false);
    }

    public static Email from(com.google.gdata.data.extensions.Email o) {
        if(o == null){
            return null;
        }
        return new Email(o.getAddress(), o.getLabel(), o.getRel(), o.getDisplayName(), o.getQuota(), o.getPrimary());
    }

    public static Email from(Json j) {
        if(j == null){
            return null;
        }
        return new Email(j.string("address"), j.string("label"), j.string("rel"), j.string("displayName"), j.string("quota"), j.bool("primary"));
    }

    public static List<Email> from(Collection l) {
        List<Email> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                Email email = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        email = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.extensions.Email) {
                        email = from((com.google.gdata.data.extensions.Email) o);
                    }
                } finally {
                    if(email != null){
                        list.add(email);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.extensions.Email toObject(){
        final com.google.gdata.data.extensions.Email o = new com.google.gdata.data.extensions.Email();
        o.setAddress(address);
        o.setLabel(label);
        o.setRel(rel);
        o.setDisplayName(displayName);
        o.setQuota(quota);
        o.setPrimary(primary);

        if(StringUtils.isBlank(o.getRel())) {
            if (StringUtils.isBlank(o.getLabel())) {
                o.setLabel("email");
            }
        } else {
            switch (o.getRel().toLowerCase()){
                case "home":
                    o.setRel(com.google.gdata.data.extensions.Email.Rel.HOME);
                    break;
                case "work":
                    o.setRel(com.google.gdata.data.extensions.Email.Rel.WORK);
                    break;
                case "other":
                    o.setRel(com.google.gdata.data.extensions.Email.Rel.OTHER);
                    break;
            }
        }

        return o;
    }

    @Override
    public Json toJson(){
        Json j = Json.map()
                .setIfNotEmpty("address", address)
                .setIfNotEmpty("label", label)
                .setIfNotEmpty("rel", rel)
                .setIfNotEmpty("displayName", displayName)
                .setIfNotEmpty("quota", quota);

        if(primary){
            j.set("primary", true);
        }
        return j;
    }
}
