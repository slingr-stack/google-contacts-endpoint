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
public class PostalAddress extends Field<com.google.gdata.data.extensions.PostalAddress> {
    public static final String LABEL = "postalAddress";
    public static final String LIST_LABEL = "postalAddresses";

    private String label;
    private String rel;
    private String value;
    private boolean primary = false;

    public PostalAddress(String label, String rel, String value, Boolean primary) {
        this.label = string(label);
        this.rel = string(rel);
        this.value = string(value);
        this.primary = bool(primary, false);
    }

    public static PostalAddress from(com.google.gdata.data.extensions.PostalAddress o) {
        if(o == null){
            return null;
        }
        return new PostalAddress(o.getLabel(), o.getRel(), o.getValue(), o.getPrimary());
    }

    public static PostalAddress from(Json j) {
        if(j == null){
            return null;
        }
        return new PostalAddress(j.string("label"), j.string("rel"), j.string("value"), j.bool("primary"));
    }

    public static List<PostalAddress> from(Collection l) {
        List<PostalAddress> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                PostalAddress postalAddress = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        postalAddress = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.extensions.PostalAddress){
                        postalAddress = from((com.google.gdata.data.extensions.PostalAddress) o);
                    }
                } finally {
                    if(postalAddress != null){
                        list.add(postalAddress);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.extensions.PostalAddress toObject(){
        final com.google.gdata.data.extensions.PostalAddress o = new com.google.gdata.data.extensions.PostalAddress();
        o.setLabel(label);
        o.setRel(rel);
        o.setValue(value);
        o.setPrimary(primary);

        if(StringUtils.isBlank(o.getRel())) {
            if (StringUtils.isBlank(o.getLabel())) {
                o.setLabel("postal_address");
            }
        } else {
            switch (o.getRel().toLowerCase()){
                case "home":
                    o.setRel(com.google.gdata.data.extensions.PostalAddress.Rel.HOME);
                    break;
                case "work":
                    o.setRel(com.google.gdata.data.extensions.PostalAddress.Rel.WORK);
                    break;
                case "other":
                    o.setRel(com.google.gdata.data.extensions.PostalAddress.Rel.OTHER);
                    break;
            }
        }

        return o;
    }

    @Override
    public Json toJson(){
        Json j = Json.map()
                .setIfNotEmpty("label", label)
                .setIfNotEmpty("rel", rel)
                .setIfNotEmpty("value", value);

        if(primary){
            j.set("primary", true);
        }
        return j;
    }
}
