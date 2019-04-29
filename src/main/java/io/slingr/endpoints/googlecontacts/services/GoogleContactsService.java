package io.slingr.endpoints.googlecontacts.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpResponseException;
import com.google.gdata.client.Query;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.contacts.ContactGroupEntry;
import com.google.gdata.data.contacts.ContactGroupFeed;
import com.google.gdata.util.ResourceNotFoundException;
import io.slingr.endpoints.exceptions.EndpointException;
import io.slingr.endpoints.exceptions.ErrorCode;
import io.slingr.endpoints.googlecontacts.GoogleContactsEndpoint;
import io.slingr.endpoints.googlecontacts.services.entities.ApiException;
import io.slingr.endpoints.googlecontacts.services.entities.Contact;
import io.slingr.endpoints.googlecontacts.services.entities.Field;
import io.slingr.endpoints.googlecontacts.services.entities.Group;
import io.slingr.endpoints.utils.Json;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Service class that interacts with the Google Contacts API
 *
 * <p>Created by lefunes on 16/06/15.
 */
public class GoogleContactsService {

    private static final Logger logger = LoggerFactory.getLogger(GoogleContactsService.class);

    private static final String URL_FEED = "https://www.google.com/m8/feeds";
    private static final String URL_CONTACTS = String.format("%s/contacts/default/full", URL_FEED);
    private static final String URL_GROUPS = String.format("%s/groups/default/full", URL_FEED);
    public static final String URL_GROUPS_FEED = String.format("%s/groups", URL_FEED);

    public static final String CONTACTS_FEED_PATH = "/m8/feeds/contacts/";
    public static final String GROUPS_FEED_PATH = "/m8/feeds/groups/";

    public static final String EXPIRATION_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat(EXPIRATION_TIME_FORMAT);

    private static final Pattern DATE_TIME_PATTERN = Pattern.compile("(\\d\\d\\d\\d\\-\\d\\d\\-\\d\\d[Tt]\\d\\d:\\d\\d:\\d\\d\\.\\d+(\\+|\\-))(\\d\\d)(\\d\\d)");


    private final String userId;
    private final ContactsService service;
    private final GoogleContactsEndpoint endpoint;

    public GoogleContactsService(String userId, String applicationName, String token, GoogleContactsEndpoint endpoint) {
        this.userId = userId;
        if (StringUtils.isBlank(token)) {
            throw new IllegalArgumentException("Invalid token");
        }
        if (StringUtils.isBlank(applicationName)) {
            applicationName = "Google Contacts";
        }

        final ContactsService service;
        try {
            service = new ContactsService(applicationName);

            // workaround for 'java.lang.NullPointerException: No authentication header information' issue on GData library
            service.setHeader("GData-Version", "3.0");
            service.setHeader("User-Agent", applicationName);
            service.setHeader("Authorization", "Bearer "+token);

            final GoogleCredential cd = new GoogleCredential().setAccessToken(token);
            service.setOAuth2Credentials(cd);
        } catch (Exception e) {
            String cm = String.format("Error building the contacts service [%s]", e.getMessage());
            logger.error(cm, e);
            throw ApiException.generate(cm, e);
        }
        this.service = service;
        this.endpoint = endpoint;
    }

    private String getUserEmail(String functionId){
        String userEmail = null;
        try {
            final List<Json> groups = findGroups(1, null, null, true, functionId);
            if (groups != null && !groups.isEmpty()) {
                userEmail = Field.getUserName(groups.get(0));
            }
        } catch (Exception ex){
            logger.info(String.format("Error when try to get the user email [%s]", ex.getMessage()));
        }
        return userEmail;
    }

    public List<Json> findContacts(String filter, String groupParam, Integer maxResults, Integer startIndex, Long updatedMin, Boolean showDeleted, String functionId){
        final List<Json> response = new ArrayList<>();
        try {
            final URL feedUrl = new URL(URL_CONTACTS);

            final String group;
            if(StringUtils.isBlank(groupParam)){
                group = null;
            } else {
                if(groupParam.startsWith("http")){
                    group = groupParam;
                } else {
                    group = Group.convertIdToUri(null, getUserEmail(functionId), groupParam);
                }
            }

            final Query query = new Query(feedUrl);
            addCommonQueryProperties(filter, maxResults, startIndex, updatedMin, showDeleted, group, query);

            final ContactFeed resultFeed = service.getFeed(query, ContactFeed.class);

            final List<Map<String, Object>> contacts = ContactsParserHelper.parseListOfContact(resultFeed);
            contacts.stream()
                    .map(Json::fromMap)
                    .forEach(response::add);
        } catch (HttpResponseException e) {
            endpoint.checkDisconnection(userId, e, functionId);
            throw ApiException.generate(e);
        } catch (Exception e) {
            throw ApiException.generate(e);
        }
        return response;
    }

    public Json findContactById(String contactId, String functionId) throws EndpointException {
        if(StringUtils.isBlank(contactId)){
            throw EndpointException.permanent(ErrorCode.ARGUMENT, "Empty contact id");
        }
        Json response;
        try {
            final URL entryUrl = getEntryUrl(URL_CONTACTS, contactId, CONTACTS_FEED_PATH);

            final ContactEntry contact = service.getEntry(entryUrl, ContactEntry.class);
            if(contact != null) {
                response = Contact.from(contact).toJson();
            } else {
                throw EndpointException.permanent(ErrorCode.ARGUMENT, "Resource not found");
            }
        } catch (HttpResponseException e) {
            endpoint.checkDisconnection(userId, e, functionId);
            throw ApiException.generate(e);
        } catch (ResourceNotFoundException e) {
            throw EndpointException.permanent(ErrorCode.ARGUMENT, "Resource not found");
        } catch (Exception e) {
            throw ApiException.generate(e);
        }
        return response;
    }

    public Json createContact(Json jsonContact, String functionId) throws EndpointException {
        final Json response;
        if(jsonContact == null || jsonContact.isEmpty()){
            throw EndpointException.permanent(ErrorCode.ARGUMENT, "Empty contact");
        }
        try {
            final Contact contact = Contact.from(jsonContact, getUserEmail(functionId));
            final URL postUrl = new URL(URL_CONTACTS);
            final ContactEntry createdContact = service.insert(postUrl, contact.toObject());
            if (createdContact != null) {
                response = Contact.from(createdContact).toJson();
            } else {
                throw EndpointException.permanent(ErrorCode.ARGUMENT, "Resource not found");
            }
        } catch (HttpResponseException e) {
            endpoint.checkDisconnection(userId, e, functionId);
            throw ApiException.generate(e);
        } catch (Exception e) {
            throw ApiException.generate(e);
        }
        return response;
    }

    public Json updateContact(String contactId, Json jsonChanges, String functionId) throws EndpointException {
        if(StringUtils.isBlank(contactId)){
            throw EndpointException.permanent(ErrorCode.ARGUMENT, "Empty contact id");
        }
        if(jsonChanges == null || jsonChanges.isEmpty()){
            throw EndpointException.permanent(ErrorCode.ARGUMENT, "There is not changes to apply");
        }
        Json response;
        try {
            final URL entryUrl = getEntryUrl(URL_CONTACTS, contactId, CONTACTS_FEED_PATH);

            final ContactEntry contact = service.getEntry(entryUrl, ContactEntry.class);
            if(contact != null) {
                final Json changes = Contact.from(contact).toJson();
                changes.merge(jsonChanges, false);

                final ContactEntry changedContact = Contact.from(changes).toObject();
                final ContactEntry updatedContact = service.update(entryUrl, changedContact, "*");
                if(updatedContact != null) {
                    response = Contact.from(updatedContact).toJson();
                } else {
                    throw EndpointException.permanent(ErrorCode.ARGUMENT, "Resource not found");
                }
            } else {
                throw EndpointException.permanent(ErrorCode.API, "Empty response");
            }
        } catch (HttpResponseException e) {
            endpoint.checkDisconnection(userId, e, functionId);
            throw ApiException.generate(e);
        } catch (ResourceNotFoundException e) {
            throw EndpointException.permanent(ErrorCode.API, String.format("Contact not found [%s]", contactId), e);
        } catch (Exception e) {
            throw ApiException.generate(e);
        }
        return response;
    }

    public void removeContact(String contactId, String functionId) throws EndpointException {
        if(StringUtils.isBlank(contactId)){
            throw EndpointException.permanent(ErrorCode.ARGUMENT, "Empty contact id");
        }
        try {
            final URL entryUrl = getEntryUrl(URL_CONTACTS, contactId, CONTACTS_FEED_PATH);

            final ContactEntry contact = service.getEntry(entryUrl, ContactEntry.class);
            if(contact != null) {
                contact.delete();
            } else {
                throw EndpointException.permanent(ErrorCode.ARGUMENT, "Resource not found");
            }
        } catch (HttpResponseException e) {
            endpoint.checkDisconnection(userId, e, functionId);
            throw ApiException.generate(e);
        } catch (ResourceNotFoundException e) {
            throw EndpointException.permanent(ErrorCode.API, "Resource not found");
        } catch (Exception e) {
            throw ApiException.generate(e);
        }
    }

    public Json findGroupById(String groupId, String functionId) throws EndpointException {
        if(StringUtils.isBlank(groupId)){
            throw EndpointException.permanent(ErrorCode.ARGUMENT, "Empty group id");
        }
        Json response;
        try {
            final URL entryUrl = getEntryUrl(URL_GROUPS, groupId, GROUPS_FEED_PATH);

            final ContactGroupEntry group = service.getEntry(entryUrl, ContactGroupEntry.class);
            if(group != null) {
                response = Group.from(group).toJson();
            } else {
                throw EndpointException.permanent(ErrorCode.ARGUMENT, "Resource not found");
            }
        } catch (HttpResponseException e) {
            endpoint.checkDisconnection(userId, e, functionId);
            throw ApiException.generate(e);
        } catch (ResourceNotFoundException e) {
            throw EndpointException.permanent(ErrorCode.API, "Resource not found");
        } catch (Exception e) {
            throw ApiException.generate(e);
        }
        return response;
    }

    public List<Json> findGroups(Integer maxResults, Integer startIndex, Long updatedMin, Boolean showDeleted, String functionId){
        final List<Json> response = new ArrayList<>();
        try {
            final URL feedUrl = new URL(URL_GROUPS);

            final Query query = new Query(feedUrl);
            addCommonQueryProperties(null, maxResults, startIndex, updatedMin, showDeleted, null, query);

            final ContactGroupFeed resultFeed = service.getFeed(query, ContactGroupFeed.class);

            final List<Map<String, Object>> groups = ContactsParserHelper.parseListOfGroups(resultFeed);
            groups.stream()
                    .map(Json::fromMap)
                    .forEach(response::add);
        } catch (HttpResponseException e) {
            endpoint.checkDisconnection(userId, e, functionId);
            throw ApiException.generate(e);
        } catch (Exception e) {
            throw ApiException.generate(e);
        }
        return response;
    }

    public Json createGroup(Json jsonGroup, String functionId) throws EndpointException {
        final Json response;
        if(jsonGroup == null || jsonGroup.isEmpty()){
            throw EndpointException.permanent(ErrorCode.ARGUMENT, "Empty group");
        }
        try {
            final Group group = Group.from(jsonGroup);
            final URL postUrl = new URL(URL_GROUPS);
            final ContactGroupEntry createdGroup = service.insert(postUrl, group.toObject());
            if (createdGroup != null) {
                response = Group.from(createdGroup).toJson();
            } else {
                throw EndpointException.permanent(ErrorCode.ARGUMENT, "Resource not found");
            }
        } catch (HttpResponseException e) {
            endpoint.checkDisconnection(userId, e, functionId);
            throw ApiException.generate(e);
        } catch (Exception e) {
            throw ApiException.generate(e);
        }
        return response;
    }

    public Json updateGroup(String groupId, Json jsonChanges, String functionId) throws EndpointException {
        if(StringUtils.isBlank(groupId)){
            throw EndpointException.permanent(ErrorCode.ARGUMENT, "Empty group id");
        }
        if(jsonChanges == null || jsonChanges.isEmpty()){
            throw EndpointException.permanent(ErrorCode.ARGUMENT, "There is not changes to apply");
        }
        Json response;
        try {
            final URL entryUrl = getEntryUrl(URL_GROUPS, groupId, GROUPS_FEED_PATH);

            final ContactGroupEntry group = service.getEntry(entryUrl, ContactGroupEntry.class);
            if(group != null) {
                final Json changes = Group.from(group).toJson();
                changes.merge(jsonChanges);

                final ContactGroupEntry changedGroup = Group.from(changes).toObject();
                final ContactGroupEntry updatedGroup = service.update(entryUrl, changedGroup, "*");
                if(updatedGroup != null) {
                    response = Group.from(updatedGroup).toJson();
                } else {
                    throw EndpointException.permanent(ErrorCode.ARGUMENT, "Resource not found");
                }
            } else {
                throw EndpointException.permanent(ErrorCode.API, "Empty response");
            }
        } catch (HttpResponseException e) {
            endpoint.checkDisconnection(userId, e, functionId);
            throw ApiException.generate(e);
        } catch (ResourceNotFoundException e) {
            throw EndpointException.permanent(ErrorCode.API, String.format("Group not found [%s]", groupId), e);
        } catch (Exception e) {
            throw ApiException.generate(e);
        }
        return response;
    }

    public void removeGroup(String groupId, String functionId) throws EndpointException {
        if(StringUtils.isBlank(groupId)){
            throw EndpointException.permanent(ErrorCode.ARGUMENT, "Empty group id");
        }
        try {
            final URL entryUrl = getEntryUrl(URL_GROUPS, groupId, GROUPS_FEED_PATH);

            final ContactGroupEntry group = service.getEntry(entryUrl, ContactGroupEntry.class);
            if(group != null) {
                group.delete();
            } else {
                throw EndpointException.permanent(ErrorCode.ARGUMENT, "Resource not found");
            }
        } catch (HttpResponseException e) {
            endpoint.checkDisconnection(userId, e, functionId);
            throw ApiException.generate(e);
        } catch (ResourceNotFoundException e) {
            throw EndpointException.permanent(ErrorCode.API, "Resource not found");
        } catch (Exception e) {
            throw ApiException.generate(e);
        }
    }

    private void addCommonQueryProperties(String filter, Number maxResults, Number startIndex, Long updatedMin, Boolean showDeleted, String group, Query query) {
        if(query == null){
            return;
        }
        if(StringUtils.isNotBlank(filter)){
            query.setFullTextQuery(filter.trim());
        }
        if(maxResults != null){
            query.setMaxResults(maxResults.intValue());
        }
        if(startIndex != null){
            query.setStartIndex(startIndex.intValue());
        }
        if(updatedMin != null){
            String dts = DATE_FORMAT.format(new Date(updatedMin));
            Matcher m = DATE_TIME_PATTERN.matcher(dts);
            if (m.matches()) {
                dts = m.group(1)+m.group(3)+":"+m.group(4);
            }
            DateTime dt = DateTime.parseDateTime(dts);
            query.setUpdatedMin(dt);
        }
        if(showDeleted != null && showDeleted){
            query.setStringCustomParameter("showdeleted", "true");
        }
        if(StringUtils.isNotBlank(group)){
            query.setStringCustomParameter("group", group.trim());
        }
    }

    private static URL getEntryUrl(String feed, String entryId, String check) throws MalformedURLException {
        final String uri;
        if(entryId.startsWith("http")){
            uri = entryId;
        } else {
            uri = String.format("%s/%s", feed, entryId);
        }
        if(StringUtils.isNotBlank(check) && !uri.contains(check)){
            throw EndpointException.permanent(ErrorCode.ARGUMENT, String.format("Invalid entryId [%s], does not include [%s]", uri, check));
        }
        return new URL(uri);
    }
}
