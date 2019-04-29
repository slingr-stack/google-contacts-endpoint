package io.slingr.endpoints.googlecontacts.services.entities;

import io.slingr.endpoints.utils.Json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Created by lefunes on 23/06/15.
 */
public class Im extends Field<com.google.gdata.data.extensions.Im> {
    public static final String LABEL = "im";
    public static final String LIST_LABEL = "ims";

    private String address;
    private String label;
    private String rel;
    private String protocol;
    private boolean primary = false;

    public Im(String address, String label, String rel, String protocol, Boolean primary) {
        this.address = string(address);
        this.label = string(label);
        this.rel = string(rel);
        this.protocol = string(protocol);
        this.primary = bool(primary, false);
    }

    public static Im from(com.google.gdata.data.extensions.Im o) {
        if(o == null){
            return null;
        }
        return new Im(o.getAddress(), o.getLabel(), o.getRel(), o.getProtocol(), o.getPrimary());
    }

    public static Im from(Json j) {
        if(j == null){
            return null;
        }
        return new Im(j.string("address"), j.string("label"), j.string("rel"), j.string("protocol"), j.bool("primary"));
    }

    public static List<Im> from(Collection l) {
        List<Im> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                Im im = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        im = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.extensions.Im){
                        im = from((com.google.gdata.data.extensions.Im) o);
                    }
                } finally {
                    if(im != null){
                        list.add(im);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.extensions.Im toObject(){
        return new com.google.gdata.data.extensions.Im(address, label, primary, protocol, rel);
    }

    @Override
    public Json toJson(){
        Json j = Json.map()
                .setIfNotEmpty("address", address)
                .setIfNotEmpty("label", label)
                .setIfNotEmpty("rel", rel)
                .setIfNotEmpty("protocol", protocol);

        if(primary){
            j.set("primary", true);
        }
        return j;
    }
}
