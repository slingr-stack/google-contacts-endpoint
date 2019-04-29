package io.slingr.endpoints.googlecontacts.services.entities;

import io.slingr.endpoints.utils.Json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Created by lefunes on 19/06/15.
 */
public class ExtendedProperty extends Field<com.google.gdata.data.extensions.ExtendedProperty> {
    public static final String LABEL = "extendedProperty";
    public static final String LIST_LABEL = "extendedProperties";

    private String name;
    private String value;
    private String realm;

    public ExtendedProperty(String name, String value, String realm) {
        this.name = string(name);
        this.value = string(value);
        this.realm = string(realm);
    }

    public static ExtendedProperty from(com.google.gdata.data.extensions.ExtendedProperty o) {
        if(o == null){
            return null;
        }
        return new ExtendedProperty(o.getName(), o.getValue(), o.getRealm());
    }

    public static ExtendedProperty from(Json j) {
        if(j == null){
            return null;
        }
        return new ExtendedProperty(j.string("name"), j.string("value"), j.string("realm"));
    }

    public static List<ExtendedProperty> from(Collection l) {
        List<ExtendedProperty> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                ExtendedProperty extendedProperty = null;
                try {
                    if (o instanceof Json || o instanceof Map) {
                        extendedProperty = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.extensions.ExtendedProperty) {
                        extendedProperty = from((com.google.gdata.data.extensions.ExtendedProperty) o);
                    }
                } finally {
                    if(extendedProperty != null){
                        list.add(extendedProperty);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.extensions.ExtendedProperty toObject(){
        final com.google.gdata.data.extensions.ExtendedProperty ep = new com.google.gdata.data.extensions.ExtendedProperty();
        ep.setName(name);
        ep.setValue(value);
        ep.setRealm(realm);

        return ep;
    }

    @Override
    public Json toJson(){
        return Json.map()
                .setIfNotEmpty("name", name)
                .setIfNotEmpty("value", value)
                .setIfNotEmpty("realm", realm);
    }
}
