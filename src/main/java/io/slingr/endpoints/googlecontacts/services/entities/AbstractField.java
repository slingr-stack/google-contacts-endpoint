package io.slingr.endpoints.googlecontacts.services.entities;

import com.google.gdata.data.Extension;

/**
 * <p>Created by lefunes on 19/06/15.
 */
public abstract class AbstractField<T extends Extension> extends Field<T> {

    public abstract void addToObject(T be);

    @Override
    public T toObject(){
        // abstract class
        return null;
    }
}
