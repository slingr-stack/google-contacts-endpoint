package io.slingr.endpoints.googlecontacts.services.entities;

import io.slingr.endpoints.utils.Json;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Created by lefunes on 23/06/15.
 */
public class Person extends Field<com.google.gdata.data.Person> {
    public static final String LABEL = "person";
    public static final String LIST_LABEL = "persons";

    private String email;
    private String name;
    private String nameLang;
    private String uri;

    public Person(String email, String name, String nameLang, String uri) {
        this.email = string(email);
        this.name = string(name);
        this.nameLang = string(nameLang);
        this.uri = string(uri);
    }

    public static Person from(com.google.gdata.data.Person o) {
        if(o == null){
            return null;
        }
        return new Person(o.getEmail(), o.getName(), o.getNameLang(), o.getUri());
    }

    public static Person from(Json j) {
        if(j == null){
            return null;
        }
        return new Person(j.string("email"), j.string("name"), j.string("nameLang"), j.string("uri"));
    }

    public static List<Person> from(Collection l) {
        List<Person> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                Person person = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        person = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.Person){
                        person = from((com.google.gdata.data.Person) o);
                    }
                } finally {
                    if(person != null){
                        list.add(person);
                    }
                }
            }
        }
        return list;
    }
    @Override
    public com.google.gdata.data.Person toObject(){
        final com.google.gdata.data.Person p = new com.google.gdata.data.Person(name, uri, email);
        if(StringUtils.isNotBlank(nameLang)){
            p.setNameLang(nameLang);
        }
        return p;
    }

    @Override
    public Json toJson(){
        return Json.map()
                .setIfNotEmpty("email", email)
                .setIfNotEmpty("name", name)
                .setIfNotEmpty("nameLang", nameLang)
                .setIfNotEmpty("uri", uri);
    }
}
