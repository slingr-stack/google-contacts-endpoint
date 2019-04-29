package io.slingr.endpoints.googlecontacts.services.entities;

import com.google.gdata.data.contacts.ContactGroupEntry;
import com.google.gdata.data.extensions.Deleted;
import io.slingr.endpoints.googlecontacts.services.GoogleContactsService;
import io.slingr.endpoints.utils.Json;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Created by lefunes on 19/06/15.
 */
public class Group extends Field<ContactGroupEntry> {
    public static final String LABEL = "group";
    public static final String LIST_LABEL = "groups";

    private BaseEntry baseEntry;
    private SystemGroup systemGroup;
    private List<ExtendedProperty> extendedProperties;
    private boolean deleted = false;

    public Group(BaseEntry baseEntry, SystemGroup systemGroup, List<ExtendedProperty> extendedProperties, Boolean deleted) {
        this.baseEntry = baseEntry;
        this.systemGroup = systemGroup;
        this.extendedProperties = extendedProperties;
        this.deleted = bool(deleted, false);
    }

    public static Group from(ContactGroupEntry o) {
        if(o == null){
            return null;
        }
        return new Group(BaseEntry.from(o), SystemGroup.from(o.getSystemGroup()), ExtendedProperty.from(o.getExtendedProperties()), o.getDeleted() != null);
    }

    public static Group from(Json j) {
        if(j == null){
            return null;
        }
        return new Group(BaseEntry.from(j), SystemGroup.from(j), ExtendedProperty.from(j.objects("extendedProperties")), j.bool("deleted"));
    }

    public static List<Group> from(Collection l) {
        List<Group> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                Group group = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        group = from(Json.fromObject(o));
                    } else if (o instanceof ContactGroupEntry) {
                        group = from((ContactGroupEntry) o);
                    }
                } finally {
                    if(group != null){
                        list.add(group);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public ContactGroupEntry toObject(){
        final ContactGroupEntry c = new ContactGroupEntry();
        baseEntry.addToObject(c);
        c.setSystemGroup(systemGroup != null ? systemGroup.toObject() : null);
        c.setDeleted(deleted ? new Deleted() : null);
        if(extendedProperties != null){
            for (ExtendedProperty extendedProperty : extendedProperties) {
                c.addExtendedProperty(extendedProperty.toObject());
            }
        }
        return c;
    }

    @Override
    public Json toJson(){
        final Json j = baseEntry.toJson();
        if(deleted){
            j.set("deleted", true);
        }
        if(systemGroup != null){
            systemGroup.addToJson(j);
        }
        if(extendedProperties != null){
            final List<Map> jList = new ArrayList<>();
            extendedProperties.stream()
                    .map(ExtendedProperty::toJson)
                    .map(Json::toMap)
                    .forEach(jList::add);
            j.setIfNotEmpty("extendedProperties", jList);
        }
        generateId(j, "groupId");
        generateName(j, "groupName");

        return j;
    }

    public static String convertIdToUri(String href, String userName, String id) {
        String uri = href;
        if(StringUtils.isBlank(uri) && StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(id)) {
            uri = String.format("%s/%s/base/%s", GoogleContactsService.URL_GROUPS_FEED, userName, id);
        }
        return uri;
    }

    public static String convertUriToId(String href, String userName, String id) {
        String idg = id;
        if(StringUtils.isBlank(idg) && StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(href)) {
            String baseUri = String.format("%s/%s/base/", GoogleContactsService.URL_GROUPS_FEED, userName);
            if(StringUtils.isNotBlank(baseUri)){
                if(href.startsWith(baseUri)){
                    idg = href.substring(baseUri.length());
                } else {
                    baseUri = baseUri.replace("https://", "http://");
                    if(href.startsWith(baseUri)){
                        idg = href.substring(baseUri.length());
                    }
                }
            }
        }
        return idg;
    }
}
