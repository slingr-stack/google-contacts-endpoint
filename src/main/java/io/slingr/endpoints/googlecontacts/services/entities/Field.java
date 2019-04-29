package io.slingr.endpoints.googlecontacts.services.entities;

import com.google.gdata.data.DateTime;
import com.google.gdata.data.Extension;
import io.slingr.endpoints.utils.Json;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Created by lefunes on 19/06/15.
 */
public abstract class Field<T extends Extension> {

    public static String string(String value){
        return string(value, null);
    }

    public static String string(String value, String defaultValue){
        return StringUtils.defaultIfBlank(value, defaultValue);
    }

    public static Boolean bool(Boolean value){
        return bool(value, null);
    }

    public static Boolean bool(Boolean value, Boolean defaultValue){
        return value != null ? value.booleanValue() : defaultValue;
    }

    public static DateTime date(String value){
        if(StringUtils.isBlank(value)){
            return null;
        }
        return dateTime(DateTime.parseDate(value));
    }

    public static DateTime dateTime(String value){
        if(StringUtils.isBlank(value)){
            return null;
        }
        return dateTime(DateTime.parseDateTimeChoice(value));
    }

    public static DateTime dateTime(DateTime value){
        return dateTime(value, null);
    }

    public static DateTime dateTime(DateTime value, DateTime defaultValue){
        return value != null ? value : defaultValue;
    }

    public static String dateTimeString(DateTime value){
        return dateTimeString(value, null);
    }

    public static String dateTimeString(DateTime value, String defaultValue){
        return value != null ? value.toString() : defaultValue;
    }

    public static void generateId(Json result, String label){
        if(result != null && !result.isEmpty()) {
            generateId(result, label, result.string("id"));
        }
    }

    public static void generateId(Json result, String label, String id){
        if (StringUtils.isNotBlank(id)) {
            int last = StringUtils.lastIndexOf(id, "/");
            if (last >= 0) {
                final String groupId = id.substring(last + 1);
                if (StringUtils.isNotBlank(groupId)) {
                    result.set(label, groupId.trim());
                }
            }
        }
    }

    public static String getUserName(Json result){
        return getUserName(result.string("id"));
    }

    public static String getUserName(String id){
        if (StringUtils.isNotBlank(id)) {
            Matcher matcher = Pattern.compile(".*/(\\S+)/base/.*").matcher(id);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return null;
    }

    public static void generateName(Json result, String label){
        if(result != null && !result.isEmpty()) {
            final Json title = result.json("title");
            if (title != null) {
                final String name = title.string("text");
                if (StringUtils.isNotBlank(name)) {
                    result.set(label, name.trim());
                }
            }
        }
    }

    public abstract T toObject();
    public abstract Json toJson();
}
