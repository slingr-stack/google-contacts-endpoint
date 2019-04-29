package io.slingr.endpoints.googlecontacts.services;

import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.contacts.ContactGroupFeed;
import io.slingr.endpoints.googlecontacts.services.entities.Contact;
import io.slingr.endpoints.googlecontacts.services.entities.Group;
import io.slingr.endpoints.utils.Json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * Created by lefunes on 18/06/15.
 */
public class ContactsParserHelper {

    public static List<Map<String, Object>> parseListOfGroups(ContactGroupFeed groupFeed){
        final List<Map<String, Object>> list = new ArrayList<>();
        groupFeed.getEntries().stream()
                .map(Group::from)
                .map(Group::toJson)
                .filter(Objects::nonNull)
                .filter(Json::isNotEmpty)
                .map(Json::toMap)
                .forEach(list::add);
        return list;
    }

    public static List<Map<String, Object>> parseListOfContact(ContactFeed contactFeed){
        final List<Map<String, Object>> list = new ArrayList<>();
        contactFeed.getEntries().stream()
                .map(Contact::from)
                .map(Contact::toJson)
                .filter(Objects::nonNull)
                .filter(Json::isNotEmpty)
                .map(Json::toMap)
                .forEach(list::add);
        return list;
    }
}
