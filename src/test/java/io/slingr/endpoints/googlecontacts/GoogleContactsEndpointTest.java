package io.slingr.endpoints.googlecontacts;

import io.slingr.endpoints.googlecontacts.services.entities.Content;
import io.slingr.endpoints.googlecontacts.services.entities.Email;
import io.slingr.endpoints.googlecontacts.services.entities.Name;
import io.slingr.endpoints.googlecontacts.services.entities.TextConstruct;
import io.slingr.endpoints.services.exchange.ReservedName;
import io.slingr.endpoints.services.rest.RestMethod;
import io.slingr.endpoints.utils.Json;
import io.slingr.endpoints.utils.tests.EndpointTests;
import io.slingr.endpoints.ws.exchange.WebServiceResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 *
 * Created by lefunes on 19/06/15.
 */
public class GoogleContactsEndpointTest  {

    private static final Logger logger = LoggerFactory.getLogger(GoogleContactsEndpointTest.class);

    // random user id
    private static final String USER_ID = "BdpleDSsZbkcuOkdSzzCRHDh";

    // ----------------------------------------------------------------
    // STEPS TO GENERATE TOKENS FOR TESTS
    // ----------------------------------------------------------------
    // 1º) Generate code using the browser using the URL generated by 'testAuthenticationUrl()' test case
    // Change the USER_CODE with the new code:
    private static final String USER_CODE = "4/AACAahBk7oEYzYwFxNQO--1wEXwKkdarqh25xLo2fvV4PXGDkOCNUGOrBPcBb-353gWJ4bqcBmb0LiF3VryI9EI#";
    // ----------------------------------------------------------------
    // 2º) Generate the refresh token using the 'testGenerateAndSaveTokens()' test case
    // Change the credentials with the information generated:
    private static final String REFRESH_TOKEN = "1/S_kN4WvLZmDJ702suvQZvzghpCh_VPUjV18vl_CmP4LQ9mFC0mk04KQz3OmtYIFZ";
    private static final String USER_NAME = "Test Integrations";
    private static final String USER_PICTURE = "https://lh6.googleusercontent.com/-TqRx4AMDL7g/AAAAAAAAAAI/AAAAAAAAABY/IYsn51oD9cc/photo.jpg";
    // ----------------------------------------------------------------
    // 3º) You can invalidate a refresh token using the 'testInvalidateRefreshTokens()' test case
    // ----------------------------------------------------------------

    private static EndpointTests test;

    @BeforeClass
    public static void init() throws Exception {
        test = EndpointTests.start(new io.slingr.endpoints.googlecontacts.Runner(), "test.properties");
    }

    @Test
    public void testAuthenticationUrl(){
        logger.info("-- testing 'authenticationUrl' function");
        Json response;

        assertTrue(test.getReceivedEvents().isEmpty());

        response = test.executeFunction("authenticationUrl");
        assertNotNull(response);
        logger.info("------------------- AUTHENTICATION URL -----------------------");
        logger.info(String.format("URL [%s]", response.string("url")));
        assertEquals("https://accounts.google.com/o/oauth2/auth?access_type=offline&approval_prompt=force&client_id=991566224127-fdtamj0e71bfs2nc32iireuc9lgr4om8.apps.googleusercontent.com&redirect_uri=http://localhost:8000/callback&response_type=code&scope=https://www.googleapis.com/auth/userinfo.profile%20https://www.google.com/m8/feeds/%20https://www.googleapis.com/auth/contacts.readonly&state=test1", response.string("url"));
        logger.info("--------------------------------------------------------------");

        assertTrue(test.getReceivedEvents().isEmpty());

        logger.info("-- END");
    }

    @Test
    @Ignore("Use to generate the tokens for the user to execute the tests")
    public void testGenerateAndSaveTokens(){
        logger.info("-- testing 'connect' function");
        Json response, configuration;

        cleanConnectedUsers();

        test.clearReceivedEvents();

        response = test.executeFunction(ReservedName.CONNECT_USER, Json.map().set(GoogleContactsEndpoint.PROPERTY_CODE, USER_CODE), USER_ID);
        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(USER_ID, response.string("userId"));

        configuration = response.json("configuration");
        assertNotNull(configuration);
        assertFalse(configuration.isEmpty());
        assertTrue(StringUtils.isNotBlank(configuration.string("refreshToken")));

        logger.info("--------------------- USER CONNECTED -------------------------");
        logger.info(String.format("User connected [%s]", configuration.string("result")));
        logger.info(String.format("Refresh Token [%s]", configuration.string("refreshToken")));
        logger.info(String.format("Name [%s]", configuration.string("name")));
        logger.info(String.format("Picture [%s]", configuration.string("picture")));
        logger.info("--------------------------------------------------------------");

        assertTrue(StringUtils.isNotBlank(configuration.string("token")));
        assertTrue(StringUtils.isNotBlank(configuration.string("expirationTime")));
        assertTrue(configuration.string("result").startsWith("Connection established as"));

        checkConnectedUser(false);

        final Json user = test.getUserDataStoreItems().get(0);
        assertEquals(user.string("_id"), configuration.string("_id"));
        assertEquals(user.string("expirationTime"), configuration.string("expirationTime"));
        assertEquals(user.string("lastCode"), configuration.string("lastCode"));
        assertEquals(user.string("name"), configuration.string("name"));
        assertEquals(user.string("picture"), configuration.string("picture"));
        assertEquals(user.string("refreshToken"), configuration.string("refreshToken"));
        assertEquals(user.string("result"), configuration.string("result"));
        assertEquals(user.string("token"), configuration.string("token"));

        List<Json> events = test.getReceivedEvents();
        assertFalse(events.isEmpty());
        assertEquals(1, events.size());

        Json event = events.get(0);
        assertNotNull(event);
        assertFalse(event.isEmpty());
        assertEquals(ReservedName.USER_CONNECTED, event.string("event"));
        assertEquals(USER_ID, event.string("userId"));

        Json data = event.json("data");
        assertNotNull(data);
        assertFalse(data.isEmpty());
        assertEquals(USER_ID, data.string("userId"));

        configuration = data.json("configuration");
        assertNotNull(configuration);
        assertFalse(configuration.isEmpty());
        assertEquals(user.string("_id"), configuration.string("_id"));
        assertEquals(user.string("expirationTime"), configuration.string("expirationTime"));
        assertEquals(user.string("lastCode"), configuration.string("lastCode"));
        assertEquals(user.string("name"), configuration.string("name"));
        assertEquals(user.string("picture"), configuration.string("picture"));
        assertEquals(user.string("refreshToken"), configuration.string("refreshToken"));
        assertEquals(user.string("result"), configuration.string("result"));
        assertEquals(user.string("token"), configuration.string("token"));

        test.clearReceivedEvents();

        logger.info("-- END");
    }

    @Test
    @Ignore("Use to disable a refresh token")
    public void testInvalidateRefreshTokens(){
        logger.info("-- testing 'disconnect' function");
        Json response, configuration;
        List<Json> contacts, events;

        test.clearReceivedEvents();

        response = test.executeFunction(ReservedName.DISCONNECT_USER, Json.map(), USER_ID);
        assertNotNull(response);
        assertFalse(response.isEmpty());

        configuration = response.json("configuration");
        assertNotNull(configuration);
        assertFalse(configuration.isEmpty());
        assertEquals("Connection disabled.", configuration.string("result"));

        events = test.getReceivedEvents();
        assertFalse(events.isEmpty());
        for (Json event : events) {
            assertNotNull(event);
            assertFalse(event.isEmpty());
            assertEquals(ReservedName.USER_DISCONNECTED, event.string("event"));
            assertEquals(USER_ID, event.string("userId"));
        }
        test.clearReceivedEvents();
        assertTrue(test.getReceivedEvents().isEmpty());

        response = test.executeFunction("_findContacts", Json.map(), USER_ID, true);
        assertNotNull(response);
        assertEquals("Invalid user configuration", response.string("message"));

        events = test.getReceivedEvents();
        assertFalse(events.isEmpty());
        for (Json event : events) {
            assertNotNull(event);
            assertFalse(event.isEmpty());
            assertEquals(ReservedName.USER_DISCONNECTED, event.string("event"));
            assertEquals(USER_ID, event.string("userId"));
        }
        test.clearReceivedEvents();
        assertTrue(test.getReceivedEvents().isEmpty());

        // creates the default user on DB when the test begin
        createConnectedUser();

        response = test.executeFunction("_findContacts", Json.map(), USER_ID);
        assertNotNull(response);
        contacts = response.jsons("contacts");
        assertNotNull(contacts);
        assertFalse(contacts.isEmpty());

        response = test.executeFunction(ReservedName.DISCONNECT_USER, Json.map(), USER_ID);
        assertNotNull(response);
        assertFalse(response.isEmpty());

        configuration = response.json("configuration");
        assertNotNull(configuration);
        assertFalse(configuration.isEmpty());
        assertEquals("Connection disabled.", configuration.string("result"));

        assertTrue(test.getUserDataStoreItems().isEmpty());

        // disconnect user, invalidate refresh token
        events = test.getReceivedEvents();
        assertFalse(events.isEmpty());
        for (Json event : events) {
            assertNotNull(event);
            assertFalse(event.isEmpty());
            assertEquals(ReservedName.USER_DISCONNECTED, event.string("event"));
            assertEquals(USER_ID, event.string("userId"));
        }
        test.clearReceivedEvents();
        assertTrue(test.getReceivedEvents().isEmpty());

        response = test.executeFunction("_findContacts", Json.map(), USER_ID, true);
        assertNotNull(response);
        assertEquals("Invalid user configuration", response.string("message"));

        events = test.getReceivedEvents();
        assertFalse(events.isEmpty());
        for (Json event : events) {
            assertNotNull(event);
            assertFalse(event.isEmpty());
            assertEquals(ReservedName.USER_DISCONNECTED, event.string("event"));
            assertEquals(USER_ID, event.string("userId"));
        }
        test.clearReceivedEvents();
        assertTrue(test.getReceivedEvents().isEmpty());

        // waits for a while
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            // do nothing
        }

        // creates the default user on DB with the invalid refresh token
        createConnectedUser();

        response = test.executeFunction("_findContacts", Json.map(), USER_ID, true);
        assertNotNull(response);
        assertTrue(response.string("message").contains("Token has been expired or revoked."));

        events = test.getReceivedEvents();
        assertFalse(events.isEmpty());
        for (Json event : events) {
            assertNotNull(event);
            assertFalse(event.isEmpty());
            assertEquals(ReservedName.USER_DISCONNECTED, event.string("event"));
            assertEquals(USER_ID, event.string("userId"));
        }
        test.clearReceivedEvents();
        assertTrue(test.getReceivedEvents().isEmpty());

        // cleans the users at the end of the test
        cleanConnectedUsers();

        assertTrue(test.getReceivedEvents().isEmpty());
        test.clearReceivedEvents();

        logger.info("-- END");
    }

    @Test
    public void functionConnectDisconnect(){
        logger.info("-- testing invalid 'connect' and 'disconnect' function");
        Json response;

        assertTrue(test.getReceivedEvents().isEmpty());

        response = test.executeFunction(ReservedName.DISCONNECT_USER, Json.map(), true);
        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals("User ID is required", response.string("message"));

        response = test.executeFunction(ReservedName.CONNECT_USER, Json.map(), true);
        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals("User ID is required", response.string("message"));

        assertTrue(test.getReceivedEvents().isEmpty());

        logger.info("-- END");
    }

    @Test
    public void wsCallback(){
        logger.info("-- testing '/callback' web services");
        WebServiceResponse response;

        assertTrue(test.getReceivedEvents().isEmpty());

        response = test.executeWebServices(RestMethod.GET, "/callback");
        assertNotNull(response);
        assertEquals("ok", response.getBody());

        response = test.executeWebServices(RestMethod.HEAD, "/callback");
        assertNotNull(response);
        assertEquals("ok", response.getBody());

        response = test.executeWebServices(RestMethod.POST, "/callback");
        assertNotNull(response);
        assertEquals("ok", response.getBody());

        response = test.executeWebServices(RestMethod.PUT, "/callback");
        assertNotNull(response);
        assertEquals("ok", response.getBody());

        assertTrue(test.getReceivedEvents().isEmpty());

        logger.info("-- END");
    }

    @Test
    public void testGetUserInformation() {
        logger.info("-- testing mocked user information");

        // creates the default user on DB when the test begin
        createConnectedUser();

        // cleans the users at the end of the test
        cleanConnectedUsers();

        logger.info("-- END");
    }

    /**
     * Creates a default user
     */
    private void createConnectedUser(){
        cleanConnectedUsers();

        test.addUserDataStoreItem(Json.map()
                .set("_id", USER_ID)
                .set("expirationTime", "2017-07-12T18:00:22.203-0300") // expired token
                .set("lastCode", USER_CODE)
                .set("name", USER_NAME)
                .set("picture", USER_PICTURE)
                .set("refreshToken", REFRESH_TOKEN)
                .set("result", String.format("Connection established as %s.", USER_NAME))
                .set("token", "-")
        );

        checkConnectedUser();
    }

    /**
     * Check the stored default user
     */
    private void checkConnectedUser(){
        checkConnectedUser(true);
    }

    /**
     * Check the stored default user
     */
    private void checkConnectedUser(boolean checkRefreshToken){
        assertFalse(test.getUserDataStoreItems().isEmpty());
        final Json user = test.getUserDataStoreItems().get(0);
        assertEquals(USER_ID, user.string("_id"));
        assertEquals(USER_CODE, user.string("lastCode"));
        assertEquals(USER_NAME, user.string("name"));
        assertEquals(USER_PICTURE, user.string("picture"));
        if(checkRefreshToken) {
            assertEquals(REFRESH_TOKEN, user.string("refreshToken"));
        }
        assertEquals(String.format("Connection established as %s.", USER_NAME), user.string("result"));
        assertTrue(StringUtils.isNotBlank(user.string("expirationTime")));
        assertFalse(StringUtils.isBlank(user.string("token")));
    }

    /**
     * Delete the default user
     */
    private void cleanConnectedUsers(){
        test.clearUserDataStore();
        assertTrue(test.getUserDataStoreItems().isEmpty());
    }

    @Test
    public void functionFindContacts(){
        logger.info("-- testing FIND CONTACTS function");
        Json response;
        List<Json> contacts;

        // creates the default user on DB when the test begin
        createConnectedUser();

        response = test.executeFunction("_findContacts", Json.map(), USER_ID);
        assertNotNull(response);
        contacts = response.jsons("contacts");
        assertNotNull(contacts);
        assertFalse(contacts.isEmpty());

        logger.info("------------------------------------");
        logger.info("CONTACTS");
        for (Object contact : contacts) {
            logger.info(String.format("- [%s]", Json.fromObject(contact).toString()));
        }
        logger.info("------------------------------------");

        // cleans the users at the end of the test
        cleanConnectedUsers();
        assertTrue(test.getReceivedEvents().isEmpty());

        logger.info("-- END");
    }

    @Test
    public void testFindGroups() {
        logger.info("-- testing FIND GROUPS function");
        Json response;
        List<Json> groups;

        // creates the default user on DB when the test begin
        createConnectedUser();

        response = test.executeFunction("_findGroups", Json.map(), USER_ID);
        assertNotNull(response);
        groups = response.jsons("groups");
        assertNotNull(groups);
        assertFalse(groups.isEmpty());

        logger.info("------------------------------------");
        logger.info("GROUPS");
        for (Object group : groups) {
            logger.info(String.format("- [%s]", Json.fromObject(group).toString()));
        }
        logger.info("------------------------------------");

        // cleans the users at the end of the test
        cleanConnectedUsers();
        assertTrue(test.getReceivedEvents().isEmpty());

        logger.info("-- END");
    }

    @Test
    public void functionGetUserInformation(){
        logger.info("-- testing GET USER INFORMATION function");
        Json response;

        // creates the default user on DB when the test begin
        createConnectedUser();

        response = test.executeFunction("getUserInformation", Json.map(), USER_ID);
        assertNotNull(response);
        assertTrue(response.bool("status"));

        Json information = response.json("information");
        assertNotNull(information);
        assertNotNull(information.string("id"));
        assertEquals(USER_NAME, information.string("name"));
        assertNotNull(information.string("given_name"));
        assertTrue(USER_NAME.contains(information.string("given_name")));
        assertNotNull(information.string("family_name"));
        assertTrue(USER_NAME.contains(information.string("family_name")));
        assertEquals("en", information.string("locale"));
        assertEquals(USER_PICTURE, information.string("picture"));

        // cleans the users at the end of the test
        cleanConnectedUsers();
        assertTrue(test.getReceivedEvents().isEmpty());

        logger.info("-- END");
    }

    @Test
    public void contactFunctions() throws InterruptedException {
        logger.info("-- testing CONTACTS functions");
        Json response;
        List<Json> contacts, lstTmp;
        Json contact, createdContact, foundContact, updatedContact, tmp;

        response = test.executeFunction("_findContacts", true);
        assertNotNull(response);
        assertEquals("Invalid user configuration", response.string("message"));

        response = test.executeFunction("_findContacts", Json.map(), USER_ID, true);
        assertNotNull(response);
        assertEquals("Invalid user configuration", response.string("message"));

        assertFalse(test.getReceivedEvents().isEmpty());
        test.clearReceivedEvents();

        // creates the default user on DB when the test begin
        createConnectedUser();

        // constants to do the tests
        final String created = "Created by Contacts API";
        final String labelCreated = String.format("\"%s\"", created);
        final String updated = "Updated by Contacts API";
        final String labelUpdated = String.format("\"%s\"", updated);
        final String emailAddress01 = "test01@slingr.io";
        final String emailAddress02 = "test02@slingr.io";
        final String emailAddress03 = "test03@slingr.io";

        response = test.executeFunction("_findContacts", Json.map().setIfNotEmpty("query", labelCreated), USER_ID);
        assertNotNull(response);
        contacts = response.jsons("contacts");
        assertNotNull(contacts);
        final int totalCreated = contacts.size();

        response = test.executeFunction("_findContacts", Json.map().setIfNotEmpty("query", labelUpdated), USER_ID);
        assertNotNull(response);
        contacts = response.jsons("contacts");
        assertNotNull(contacts);
        final int totalUpdated = contacts.size();

        contact = Json.map()
                .set("name", new Name("Test U. Ser", "Test", "U", "Ser").toJson())
                .set("content", new Content(created).toJson())
                .set("emailAddresses", Arrays.asList(
                        new Email(emailAddress01, null, "hOme", null, null, true).toJson(),
                        new Email(emailAddress02, null, "work", null, null, false).toJson()
                ));

        createdContact = test.executeFunction("_createContact", contact, USER_ID);
        assertNotNull(createdContact);
        assertNotNull(createdContact.string("id"));

        tmp = createdContact.json("content");
        assertNotNull(tmp);
        assertEquals(created, tmp.string("text"));

        tmp = createdContact.json("name");
        assertNotNull(tmp);
        assertEquals("Test U. Ser", tmp.string("fullName"));
        assertEquals("Test", tmp.string("givenName"));
        assertEquals("U", tmp.string("additionalName"));
        assertEquals("Ser", tmp.string("familyName"));

        lstTmp = createdContact.jsons("emailAddresses");
        assertNotNull(lstTmp);
        assertEquals(2, lstTmp.size());
        assertEquals(emailAddress01, lstTmp.get(0).string("address"));
        assertEquals("http://schemas.google.com/g/2005#home", lstTmp.get(0).string("rel"));
        assertNull(lstTmp.get(0).string("label"));
        assertEquals(true, lstTmp.get(0).bool("primary"));
        assertEquals(emailAddress02, lstTmp.get(1).string("address"));
        assertEquals("http://schemas.google.com/g/2005#work", lstTmp.get(1).string("rel"));
        assertNull(lstTmp.get(1).string("label"));
        assertNull(lstTmp.get(1).bool("primary"));

        final String id = createdContact.string("id");
        assertNotNull(id);
        final String contactId = createdContact.string("contactId");
        assertNotNull(contactId);
        final String contactName = createdContact.string("contactName");
        assertNotNull(contactName);
        assertEquals(createdContact.json("name").string("fullName"), contactName);

        logger.info(String.format("Contact created [%s]", contactId));

        // waits for a while
        Thread.sleep(4000);

        response = test.executeFunction("_findContacts", Json.map().setIfNotEmpty("query", labelCreated), USER_ID);
        assertNotNull(response);
        contacts = response.jsons("contacts");
        assertNotNull(contacts);
        assertEquals(totalCreated + 1, contacts.size());

        response = test.executeFunction("_findContacts", Json.map().setIfNotEmpty("query", labelUpdated), USER_ID);
        assertNotNull(response);
        contacts = response.jsons("contacts");
        assertNotNull(contacts);
        assertEquals(totalUpdated, contacts.size());

        foundContact = test.executeFunction("_findOneContact", Json.map().setIfNotEmpty("contactId", contactId), USER_ID);
        assertNotNull(foundContact);
        assertEquals(contactId, foundContact.string("contactId"));

        tmp = foundContact.json("content");
        assertNotNull(tmp);
        assertEquals(created, tmp.string("text"));

        tmp = foundContact.json("name");
        assertNotNull(tmp);
        assertEquals("Test U. Ser", tmp.string("fullName"));
        assertEquals("Test", tmp.string("givenName"));
        assertEquals("U", tmp.string("additionalName"));
        assertEquals("Ser", tmp.string("familyName"));

        Json changes = Json.map()
                .set("contactId", contactId)
                .set("content", new Content(updated).toJson())
                .set("emailAddresses", Arrays.asList(
                        new Email(emailAddress02, null, null, null, null, true).toJson(),
                        new Email(emailAddress03, null, com.google.gdata.data.extensions.Email.Rel.HOME, null, null, false).toJson()
                ));

        updatedContact = test.executeFunction("_updateContact", changes, USER_ID);
        assertNotNull(updatedContact);
        assertEquals(contactId, updatedContact.string("contactId"));

        tmp = updatedContact.json("content");
        assertNotNull(tmp);
        assertEquals(updated, tmp.string("text"));

        tmp = updatedContact.json("name");
        assertNotNull(tmp);
        assertEquals("Test U. Ser", tmp.string("fullName"));
        assertEquals("Test", tmp.string("givenName"));
        assertEquals("U", tmp.string("additionalName"));
        assertEquals("Ser", tmp.string("familyName"));

        lstTmp = updatedContact.jsons("emailAddresses");
        assertNotNull(lstTmp);
        assertEquals(2, lstTmp.size());
        assertEquals(emailAddress02, lstTmp.get(0).string("address"));
        assertNull(lstTmp.get(0).string("rel"));
        assertEquals("email", lstTmp.get(0).string("label"));
        assertEquals(true, lstTmp.get(0).bool("primary"));
        assertEquals(emailAddress03, lstTmp.get(1).string("address"));
        assertEquals("http://schemas.google.com/g/2005#home", lstTmp.get(1).string("rel"));
        assertNull(lstTmp.get(1).string("label"));
        assertNull(lstTmp.get(1).bool("primary"));

        logger.info(String.format("Contact updated [%s]", contactId));

        // waits for a while
        Thread.sleep(4000);

        response = test.executeFunction("_findContacts", Json.map().setIfNotEmpty("query", labelCreated), USER_ID);
        assertNotNull(response);
        contacts = response.jsons("contacts");
        assertNotNull(contacts);
        assertEquals(totalCreated, contacts.size());

        response = test.executeFunction("_findContacts", Json.map().setIfNotEmpty("query", labelUpdated), USER_ID);
        assertNotNull(response);
        contacts = response.jsons("contacts");
        assertNotNull(contacts);
        assertEquals(totalUpdated + 1, contacts.size());

        foundContact = test.executeFunction("_findOneContact", Json.map().setIfNotEmpty("contactId", contactId), USER_ID);
        assertNotNull(foundContact);
        assertEquals(contactId, foundContact.string("contactId"));

        tmp = foundContact.json("content");
        assertNotNull(tmp);
        assertEquals(updated, tmp.string("text"));

        tmp = foundContact.json("name");
        assertNotNull(tmp);
        assertEquals("Test U. Ser", tmp.string("fullName"));
        assertEquals("Test", tmp.string("givenName"));
        assertEquals("U", tmp.string("additionalName"));
        assertEquals("Ser", tmp.string("familyName"));

        lstTmp = foundContact.jsons("emailAddresses");
        assertNotNull(lstTmp);
        assertEquals(2, lstTmp.size());
        assertEquals(emailAddress02, lstTmp.get(0).string("address"));
        assertNull(lstTmp.get(0).string("rel"));
        assertEquals("email", lstTmp.get(0).string("label"));
        assertEquals(true, lstTmp.get(0).bool("primary"));
        assertEquals(emailAddress03, lstTmp.get(1).string("address"));
        assertEquals("http://schemas.google.com/g/2005#home", lstTmp.get(1).string("rel"));
        assertNull(lstTmp.get(1).string("label"));
        assertNull(lstTmp.get(1).bool("primary"));

        logger.info(String.format("Removing contact [%s]", contactId));
        response = test.executeFunction("_deleteContact", Json.map().setIfNotEmpty("contactId", contactId), USER_ID);
        assertNotNull(response);

        logger.info(String.format("Contact removed [%s]", contactId));

        Thread.sleep(4000);

        response = test.executeFunction("_findOneContact", Json.map().setIfNotEmpty("contactId", contactId), USER_ID, true);
        assertNotNull(foundContact);
        assertTrue("Resource not found".equalsIgnoreCase(response.string("message")));

        response = test.executeFunction("_findContacts", Json.map().setIfNotEmpty("query", labelCreated), USER_ID);
        assertNotNull(response);
        contacts = response.jsons("contacts");
        assertNotNull(contacts);
        assertEquals(totalCreated, contacts.size());

        response = test.executeFunction("_findContacts", Json.map().setIfNotEmpty("query", labelUpdated), USER_ID);
        assertNotNull(response);
        contacts = response.jsons("contacts");
        assertNotNull(contacts);
        assertEquals(totalUpdated, contacts.size());

        response = test.executeFunction("_findOneContact", Json.map().setIfNotEmpty("contactId", contactId), USER_ID, true);
        assertNotNull(response);
        assertTrue("Resource not found".equalsIgnoreCase(response.string("message")));

        // cleans the users at the end of the test
        cleanConnectedUsers();

        assertTrue(test.getReceivedEvents().isEmpty());
        test.clearReceivedEvents();

        logger.info("-- END");
    }

    @Test
    public void contactGroups() throws InterruptedException {

        logger.info("-- testing GROUPS functions");
        Json response;
        Json group, createdGroup, foundGroup, updatedGroup, tmp;

        test.clearReceivedEvents();

        // creates the default user on DB when the test begin
        createConnectedUser();

        group = Json.map().set("title", new TextConstruct("Test Group").toJson());

        createdGroup = test.executeFunction("_createGroup", group, USER_ID);
        assertNotNull(createdGroup);
        assertNotNull(createdGroup.string("id"));

        tmp = createdGroup.json("content");
        assertNotNull(tmp);
        assertEquals("Test Group", tmp.string("text"));

        tmp = createdGroup.json("title");
        assertNotNull(tmp);
        assertEquals("Test Group", tmp.string("text"));

        final String id = createdGroup.string("id");
        assertNotNull(id);
        final String groupId = createdGroup.string("groupId");
        assertNotNull(groupId);
        final String groupName = createdGroup.string("groupName");
        assertNotNull(groupName);
        assertEquals(tmp.string("text"), groupName);

        logger.info(String.format("Group created [%s]", groupId));
        Thread.sleep(8000);

        foundGroup = test.executeFunction("_findOneGroup", Json.map().setIfNotEmpty("groupId", groupId), USER_ID);
        assertNotNull(foundGroup);
        assertEquals(groupId, foundGroup.string("groupId"));

        tmp = foundGroup.json("content");
        assertNotNull(tmp);
        assertEquals("Test Group", tmp.string("text"));

        tmp = foundGroup.json("title");
        assertNotNull(tmp);
        assertEquals("Test Group", tmp.string("text"));

        Json changes = Json.map()
                .set("groupId", groupId)
                .set("title", new TextConstruct("Updated Group").toJson());

        updatedGroup = test.executeFunction("_updateGroup", changes, USER_ID);
        assertNotNull(updatedGroup);
        assertEquals(groupId, updatedGroup.string("groupId"));

        tmp = updatedGroup.json("content");
        assertNotNull(tmp);
        assertEquals("Updated Group", tmp.string("text"));

        tmp = updatedGroup.json("title");
        assertNotNull(tmp);
        assertEquals("Updated Group", tmp.string("text"));

        logger.info(String.format("Group updated [%s]", groupId));
        Thread.sleep(8000);

        foundGroup = test.executeFunction("_findOneGroup", Json.map().setIfNotEmpty("groupId", groupId), USER_ID);
        assertNotNull(foundGroup);
        assertEquals(groupId, foundGroup.string("groupId"));

        tmp = foundGroup.json("content");
        assertNotNull(tmp);
        assertEquals("Updated Group", tmp.string("text"));

        tmp = foundGroup.json("title");
        assertNotNull(tmp);
        assertEquals("Updated Group", tmp.string("text"));

        logger.info(String.format("Removing group [%s]", groupId));
        response = test.executeFunction("_deleteGroup", Json.map().setIfNotEmpty("groupId", groupId), USER_ID);
        assertNotNull(response);

        logger.info(String.format("Group removed [%s]", groupId));
        Thread.sleep(8000);

        response = test.executeFunction("_findOneGroup", Json.map().setIfNotEmpty("groupId", groupId), USER_ID, true);
        assertNotNull(response);
        assertTrue("Resource not found".equalsIgnoreCase(response.string("message")));

        response = test.executeFunction("_deleteGroup", Json.map().setIfNotEmpty("groupId", groupId), USER_ID, true);
        assertNotNull(response);
        assertTrue("Resource not found".equalsIgnoreCase(response.string("message")));

        // cleans the users at the end of the test
        cleanConnectedUsers();

        assertTrue(test.getReceivedEvents().isEmpty());

        logger.info("-- END");
    }
}