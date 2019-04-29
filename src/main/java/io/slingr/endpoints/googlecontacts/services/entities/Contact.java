package io.slingr.endpoints.googlecontacts.services.entities;

import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.extensions.Deleted;
import io.slingr.endpoints.utils.Json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>Created by lefunes on 19/06/15.
 */
public class Contact extends Field<ContactEntry> {
    public static final String LABEL = "contact";
    public static final String LIST_LABEL = "contacts";

    private BasePersonEntry basePersonEntry;
    private List<GroupMembershipInfo> groupMembershipInfos;
    private YomiName yomiName;
    private boolean deleted = false;

    public Contact(BasePersonEntry basePersonEntry, List<GroupMembershipInfo> groupMembershipInfos, YomiName yomiName, Boolean deleted) {
        this.basePersonEntry = basePersonEntry;
        this.groupMembershipInfos = groupMembershipInfos;
        this.yomiName = yomiName;
        this.deleted = bool(deleted, false);
    }

    public static Contact from(ContactEntry o) {
        if(o == null){
            return null;
        }
        return new Contact(BasePersonEntry.from(o), GroupMembershipInfo.from(getUserName(o.getId()), o.getGroupMembershipInfos()), YomiName.from(o.getYomiName()), o.getDeleted() != null);
    }

    public static Contact from(Json j) {
        return from(j, getUserName(j));
    }

    public static Contact from(Json j, String userName) {
        if(j == null){
            return null;
        }
        return new Contact(BasePersonEntry.from(j), GroupMembershipInfo.from(userName, j.objects("groupMembershipInfos")), YomiName.from(j), j.bool("deleted"));
    }

    public static List<Contact> from(Collection l) {
        List<Contact> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                Contact contact = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        contact = from(Json.fromObject(o));
                    } else if (o instanceof ContactEntry) {
                        contact = from((ContactEntry) o);
                    }
                } finally {
                    if(contact != null){
                        list.add(contact);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public ContactEntry toObject(){
        final ContactEntry c = new ContactEntry();
        basePersonEntry.addToObject(c);
        c.setYomiName(yomiName != null ? yomiName.toObject() : null);
        c.setDeleted(deleted ? new Deleted() : null);
        if(groupMembershipInfos != null){
            for (GroupMembershipInfo groupMembershipInfo : groupMembershipInfos) {
                c.addGroupMembershipInfo(groupMembershipInfo.toObject());
            }
        }
        return c;
    }

    @Override
    public Json toJson(){
        final Json j = basePersonEntry.toJson();
        if(deleted){
            j.set("deleted", true);
        }
        if(yomiName != null){
            yomiName.addToJson(j);
        }
        if(groupMembershipInfos != null){
            final List<Map> jList = groupMembershipInfos.stream()
                    .map(GroupMembershipInfo::toJson)
                    .map(Json::toMap)
                    .collect(Collectors.toList());
            j.setIfNotEmpty("groupMembershipInfos", jList);
        }
        generateId(j, "contactId");
        generateName(j, "contactName");

        return j;
    }
}
