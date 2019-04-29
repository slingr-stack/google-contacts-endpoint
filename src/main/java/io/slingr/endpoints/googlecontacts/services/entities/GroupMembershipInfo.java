package io.slingr.endpoints.googlecontacts.services.entities;

import io.slingr.endpoints.utils.Json;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Created by lefunes on 19/06/15.
 */
public class GroupMembershipInfo extends Field<com.google.gdata.data.contacts.GroupMembershipInfo> {
    public static final String LABEL = "groupMembershipInfo";
    public static final String LIST_LABEL = "groupMembershipInfoList";

    private final String userName;
    private final String id;
    private final String href;
    private boolean deleted = false;

    public GroupMembershipInfo(String userName, String id, String href, Boolean deleted) {
        this.userName = string(userName);
        this.deleted = bool(deleted, false);

        id = string(id);
        this.href = Group.convertIdToUri(string(href), userName, id);
        this.id = Group.convertUriToId(href, userName, id);
    }

    public static GroupMembershipInfo from(String userName, com.google.gdata.data.contacts.GroupMembershipInfo o) {
        if(o == null){
            return null;
        }
        return new GroupMembershipInfo(userName, null, o.getHref(), o.getDeleted());
    }

    public static GroupMembershipInfo from(String userName, Json j) {
        if(j == null){
            return null;
        }
        return new GroupMembershipInfo(userName, j.string("id"), j.string("href"), j.bool("deleted"));
    }

    public static List<GroupMembershipInfo> from(String userName, Collection l) {
        List<GroupMembershipInfo> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                GroupMembershipInfo groupMembershipInfo = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        groupMembershipInfo = from(userName, Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.contacts.GroupMembershipInfo){
                        groupMembershipInfo = from(userName, (com.google.gdata.data.contacts.GroupMembershipInfo) o);
                    }
                } finally {
                    if(groupMembershipInfo != null){
                        list.add(groupMembershipInfo);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.contacts.GroupMembershipInfo toObject(){
        return new com.google.gdata.data.contacts.GroupMembershipInfo(deleted, Group.convertIdToUri(href, userName, id));
    }

    @Override
    public Json toJson(){
        Json json = Json.map()
                .setIfNotEmpty("href", href);
        if(StringUtils.isNotBlank(this.id)){
            json.set("id", id);
        }
        if(deleted){
            json.set("deleted", true);
        }
        return json;
    }
}
