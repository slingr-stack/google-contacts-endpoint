package io.slingr.endpoints.googlecontacts.services.entities;

import io.slingr.endpoints.utils.Json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Created by lefunes on 19/06/15.
 */
public class DirectoryServer extends SimpleField<com.google.gdata.data.contacts.DirectoryServer> {
    public static final String LABEL = "directoryServer";
    public static final String LIST_LABEL = "directoryServers";

    public DirectoryServer(String value) {
        super(value);
    }

    public static DirectoryServer from(com.google.gdata.data.contacts.DirectoryServer o) {
        if(o == null){
            return null;
        }
        return new DirectoryServer(o.getValue());
    }

    public static DirectoryServer from(Json j) {
        if(j == null){
            return null;
        }
        return new DirectoryServer(j.string(LABEL));
    }

    public static List<DirectoryServer> from(Collection l) {
        List<DirectoryServer> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                DirectoryServer directoryServer = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        directoryServer = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.contacts.DirectoryServer) {
                        directoryServer = from((com.google.gdata.data.contacts.DirectoryServer) o);
                    }
                } finally {
                    if(directoryServer != null){
                        list.add(directoryServer);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.contacts.DirectoryServer toObject() {
        return new com.google.gdata.data.contacts.DirectoryServer(value);
    }

    @Override
    public void addToJson(Json json) {
        if(json != null){
            json.set(LABEL, value);
        }
    }
}
