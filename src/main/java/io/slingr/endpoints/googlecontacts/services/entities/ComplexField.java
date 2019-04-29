package io.slingr.endpoints.googlecontacts.services.entities;

import com.google.gdata.data.DateTime;
import io.slingr.endpoints.utils.Json;

/**
 * <p>Created by lefunes on 23/06/15.
 */
public abstract class ComplexField<T> {

    public static String string(String value){
        return Field.string(value);
    }

    public static String string(String value, String defaultValue){
        return Field.string(value, defaultValue);
    }

    public static Boolean bool(Boolean value){
        return Field.bool(value);
    }

    public static Boolean bool(Boolean value, Boolean defaultValue){
        return Field.bool(value, defaultValue);
    }

    public static DateTime date(String value){
        return Field.date(value);
    }

    public static DateTime dateTime(String value){
        return Field.dateTime(value);
    }

    public static DateTime dateTime(DateTime value){
        return Field.dateTime(value);
    }

    public static DateTime dateTime(DateTime value, DateTime defaultValue){
        return Field.dateTime(value, defaultValue);
    }

    public static String dateTimeString(DateTime value){
        return Field.dateTimeString(value);
    }

    public static String dateTimeString(DateTime value, String defaultValue){
        return value != null ? value.toString() : defaultValue;
    }

    public abstract T toObject();
    public abstract Json toJson();
}
