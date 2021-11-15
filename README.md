---
title: Google Contacts endpoint
keywords: 
last_updated: April 20, 2017
tags: []
summary: "Detailed description of the API of the Google Contacts endpoint."
---

## Overview

The Google Contacts endpoint is a user endpoint (see [Global vs user endpoints](app_development_model_endpoints.html#global-vs-user-endpoints)), 
which means that each user should connect to the endpoint.

This endpoints hides most of the complexities of the [Google Contacts API](https://developers.google.com/google-apps/contacts/v3/)
to make it easier to work with it, however not all features are available. Here is what's supported by the endpoint:

- Authentication and authorization
- Find, create, update and delete contacts
- Find, create, update and delete groups
- Events when contacts are created, updated or deleted on Google

## Configuration

In order to use the Google Contacts endpoint you must create an app in the [Google Developer Console](https://console.developers.google.com)
by following these instructions:

- Access to Google Developer Console
- Access to `API Manager > Library`. Enable `Contacts API`.
- Access to `API Manager > Credentials > OAuth consent screen`. Complete the form as you prefer and save it.
- Access to `API Manager > Credentials > Credentials`, then `Create credentials > OAuth client ID`.
- Select `Web application` as `Application Type` and add your domain as `Authorized Javascript Origins` (per example 
  `https://myapp.slingrs.io` or you custom domain if you have one), and add a `Authorized redirect URIs` 
  with your domain and `/callback`, like `https://myapp.slingrs.io/callback` or `https://mycustomdomain.com/callback`.
  If you plan to use the app as a template, you should select 'Multi Application' as 'Client type' in order to use the
  platform main domain, like `https://slingrs.io` and `https://slingrs.io/callback`.
- Then click on `Create`.
- That will give you the `Client ID` and `Client Secret` values.  

### Client ID

As explained above, this value comes from the app created in the Google Developer Console.

### Client secret

As explained above, this value comes from the app created in the Google Developer Console.

### Client type

This field determines what kind of URIs will be used on the client configuration on Google.
Select 'Multi Application' when you want to use your application as a template in order to
use the platform main domain.

### Javascript origin

This URL has to be configured in the app created in the Google Developer Console as a valid
origin for OAuth in the field `Authorized JavaScript origins`.

### Registered URI

This URL has to be configured in the app created in the Google Developer Console in the field
`Authorized redirect URIs`.

### Sync process

This setting indicates if the endpoint should periodically check for changes in contacts, like
for example a contact was created or updated. If disabled no events will be received from the
endpoint.

### Sync frequency

How often the endpoint will check for changes in contacts (in minutes). This value cannot be
less than 5 minutes.

## Quick start

You can create a new contact like this:

```js
sys.context.setCurrentUserByEmail('operator1@test.com');
var contactInfo = {
  name: {
    fullName: 'Contact1 Test1',
    givenName: 'Contact1',
    familyName: 'Test1'
  },
  emailAddresses: [
    {address: 'contact1@test1.com', rel: 'work', primary: true},
    {address: 'contact1@personal.com', rel: 'home', primary: false}
  ]
};
var res = app.endpoints.googleContacts.createContact(contactInfo);
log('contact created: '+JSON.stringify(res));
```

To find a contact by ID (`contactId` field) you can use this function:
 
```js
sys.context.setCurrentUserByEmail('operator1@test.com');
var res = app.endpoints.googleContacts.findOneContact('37c9e57089bce400');
log('contact: '+JSON.stringify(res));
```

When a contact is created or updated you will get the event `Contact updated` and you can handle
it like this:

```js
var info = event.data;
contact.field('googleId').val(info.contactId);
contact.field('lastName').val(info.name.familyName);
contact.field('firstName').val(info.name.givenName);
var emails = [];
if(info.emailAddresses && info.emailAddresses.length > 0){
    for(var i in info.emailAddresses){
        emails.push(info.emailAddresses[i].address);
    }
}
contact.field('emails').val(emails);
sys.data.save(contact);
```

## Data formats

This endpoints hides most of the complexities in the Google Contacts API. For example by default
Google Contacts API uses XML, but the endpoint converts everything from/to JSON. Here we will
describe the format used by the endpoint for contacts and groups.

### Contacts

This is the format of a contact:

```js
{
  "id": "http://www.google.com/m8/feeds/contacts/test.integrations%40slingr.io/base/37c9e57089bce400",
  "canEdit": true,
  "isDraft": false,
  "etag": "Qns5cDVSLSt7I2A9XR9XGUkLQA0.",
  "title": {
    "type": "text",
    "text": "Contact1 Test1"
  },
  "updated": "2017-05-16T13:36:33.528Z",
  "edited": "2017-05-16T13:36:33.528Z",
  "editLink": {
    "href": "https://www.google.com/m8/feeds/contacts/test.integrations%40slingr.io/full/37c9e57089bce400",
    "rel": "edit",
    "type": "application/atom+xml"
  },
  "selfLink": {
    "href": "https://www.google.com/m8/feeds/contacts/test.integrations%40slingr.io/full/37c9e57089bce400",
    "rel": "self",
    "type": "application/atom+xml"
  },
  "content": {
    "type": "text",
    "typeTc": "text",
    "text": "This is a test note for this contact."
  },
  "links": [
    {
      "href": "https://www.google.com/m8/feeds/photos/media/test.integrations%40slingr.io/37c9e57089bce400",
      "rel": "http://schemas.google.com/contacts/2008/rel#photo",
      "type": "image/*"
    },
    {
      "href": "https://www.google.com/m8/feeds/contacts/test.integrations%40slingr.io/full/37c9e57089bce400",
      "rel": "self",
      "type": "application/atom+xml"
    },
    {
      "href": "https://www.google.com/m8/feeds/contacts/test.integrations%40slingr.io/full/37c9e57089bce400",
      "rel": "edit",
      "type": "application/atom+xml"
    }
  ],
  "categories": [
    {
      "scheme": "http://schemas.google.com/g/2005#kind",
      "term": "http://schemas.google.com/contact/2008#contact"
    }
  ],
  "name": {
    "fullName": "Contact1 Test1",
    "givenName": "Contact1",
    "familyName": "Test1"
  },
  "contactPhotoLink": {
    "href": "https://www.google.com/m8/feeds/photos/media/test.integrations%40slingr.io/37c9e57089bce400",
    "rel": "http://schemas.google.com/contacts/2008/rel#photo",
    "type": "image/*"
  },
  "emailAddresses": [
    {
      "address": "contact1@test1.com",
      "rel": "http://schemas.google.com/g/2005#work",
      "primary": true
    },
    {
      "address": "contact1@personal.com",
      "rel": "http://schemas.google.com/g/2005#home",
      "primary": false
    }
  ],
  "phoneNumbers": [
    {
      "rel": "http://schemas.google.com/g/2005#work",
      "uri": "tel:+54-261-496-4879",
      "phoneNumber": "+5402614964879",
      "primary": false
    },
    {
      "rel": "http://schemas.google.com/g/2005#mobile",
      "uri": "tel:+54-9-261-627-8459",
      "phoneNumber": "+5492616278459",
      "primary": false
    }
  ],
  "structuredPostalAddresses": [
    {
      "rel": "http://schemas.google.com/g/2005#work",
      "city": "Godoy Cruz",
      "formattedAddress": "73 Liniers, Godoy Cruz, Mendoza",
      "region": "Mendoza",
      "street": "73 Liniers",
      "primary": false
    }
  ],
  "deleted": false,
  "groupMembershipInfos": [
    {
      "href": "http://www.google.com/m8/feeds/groups/test.integrations%40slingr.io/base/6",
      "id": "6"
    },
    {
      "href": "http://www.google.com/m8/feeds/groups/test.integrations%40slingr.io/base/2fdc5c0a8e0cd253",
      "id": "2fdc5c0a8e0cd253"
    }
  ],
  "contactId": "37c9e57089bce400",
  "contactName": "Contact1 Test1"
}
```

### Groups

This is the format of a group:

```js
{
  "id": "http://www.google.com/m8/feeds/groups/test.integrations%40slingr.io/base/2fdc5c0a8e0cd253",
  "canEdit": true,
  "isDraft": false,
  "etag": "R3g_eDVSLit7I2A9XRVaFUsPRQQ.",
  "title": {
    "type": "text",
    "text": "Developers"
  },
  "updated": "2015-08-14T15:13:26.640Z",
  "edited": "2015-08-14T15:13:26.640Z",
  "editLink": {
    "href": "https://www.google.com/m8/feeds/groups/test.integrations%40slingr.io/full/2fdc5c0a8e0cd253",
    "rel": "edit",
    "type": "application/atom+xml"
  },
  "selfLink": {
    "href": "https://www.google.com/m8/feeds/groups/test.integrations%40slingr.io/full/2fdc5c0a8e0cd253",
    "rel": "self",
    "type": "application/atom+xml"
  },
  "content": {
    "type": "text",
    "typeTc": "text",
    "text": "Developers"
  },
  "links": [
    {
      "href": "https://www.google.com/m8/feeds/groups/test.integrations%40slingr.io/full/2fdc5c0a8e0cd253",
      "rel": "self",
      "type": "application/atom+xml"
    },
    {
      "href": "https://www.google.com/m8/feeds/groups/test.integrations%40slingr.io/full/2fdc5c0a8e0cd253",
      "rel": "edit",
      "type": "application/atom+xml"
    }
  ],
  "categories": [
    {
      "scheme": "http://schemas.google.com/g/2005#kind",
      "term": "http://schemas.google.com/contact/2008#group"
    }
  ],
  "deleted": false,
  "groupId": "2fdc5c0a8e0cd253",
  "groupName": "Developers"
}
```

## Javascript API

In order to call any of these methods you have to make sure that current user is connected to the
endpoint. If the user is not connected an error will be thrown.

You can check if the user is connected like this:

```js
if (app.endpoints.googleContacts.isUserConnected()) {
  sys.logs.info('User is connected to Google Contacts endpoint');
} else {
  sys.logs.info('User is not connected to Google Contacts endpoint');
}
```

### Find one contact

```js
var res = app.endpoints.googleContacts.findOneContact(contactId);
```

Returns the contact or `null` if not found. Here is a sample:

```js
sys.context.setCurrentUserByEmail('operator1@test.com');
var res = app.endpoints.googleContacts.findOneContact('37c9e57089bce400');
if (res) {
  log('contact: '+JSON.stringify(res)); 
} else {
  sys.logs.info('Contact not found');
}
```

### Find contacts

```js
var res = app.endpoints.googleContacts.findContacts(params);
```

Returns the contacts matching the search criteria:
 
```js
{
  "contacts": [
    { ... },
    { ... },
    { ... }
  ]
}
```

Valid parameters are:

- `query`: this is a query string you can pass to filter contacts.
- `maxResults`: the maximum number of results to return.
- `startIndex`: the offset to start returning results, which is useful for pagination.
- `updatedMin`: this is a timestamp to indicate the last update time of the contact. This is useful
  if you are periodically checking for changes in contacts and you just want to know which ones
  have been modified/created after an specific timestamp.
  You can pass a timestamp in milliseconds, a Date object or use the format `yyyy-MM-dd'T'HH:mm:ss.SSSZ`.
- `group`: this is the ID of a group to filter contacts by.
- `showDeleted`: `true` if deleted contacts should be listed, `false` otherwise. `false` by default.

Here is a sample:

```js
var res = app.endpoints.googleContacts.findContacts({
  query: 'Test4',
  maxResults: 1000,
  showDeleted: true
});
log('count: '+res.contacts.length);
```

### Create contact

```js
var res = app.endpoints.googleContacts.createContact(contactInfo);
```

Returns the created contact. Here is a sample: 

```js
sys.context.setCurrentUserByEmail('operator1@test.com');
var contactInfo = {
  name: {
    fullName: 'Contact1 Test1',
    givenName: 'Contact1',
    familyName: 'Test1'
  },
  emailAddresses: [
    {address: 'contact1@test1.com', rel: 'work', primary: true},
    {address: 'contact1@personal.com', rel: 'home', primary: false}
  ]
};
var res = app.endpoints.googleContacts.createContact(contactInfo);
log('contact created: '+JSON.stringify(res));
```

### Update contact

```js
var res = app.endpoints.googleContacts.updateContact(contactInfo);
```

Returns the updated contact. Here is a sample:

```js
sys.context.setCurrentUserByEmail('operator1@test.com');
var contact = app.endpoints.googleContacts.findOneContact('37c9e57089bce400');
contact.content.text = 'The note of this contact was updated';
var res = app.endpoints.googleContacts.updateContact(contact);
log('contact updated: '+JSON.stringify(res));
```

### Delete contact

```js
app.endpoints.googleContacts.deleteContact(contactId);
```

Here is a sample:

```js
sys.context.setCurrentUserByEmail('operator1@test.com');
try {
    app.endpoints.googleContacts.deleteContact('37c9e57089bce40a');
    sys.logs.info('contact deleted');
} catch(e) {
    sys.logs.error('contact was not deleted: '+JSON.stringify(e));
}
```

### Find one group

```js
var res = app.endpoints.googleContacts.findOneGroup(groupId);
```

Returns the group or `null` if not found. Here is a sample:

```js
var res = app.endpoints.googleContacts.findOneGroup('2fdc5c0a8e0cd253');
if (res) {
  log('group: '+JSON.stringify(res));
} else {
  sys.logs.info('Group not found');
}
```

### Find groups

```js
var res = app.endpoints.googleContacts.findGroups(params);
```

Returns the groups matching the search criteria:

```js
{
  "groups": [
    { ... },
    { ... },
    { ... }
  ]
}
```

Valid parameters are:

- `maxResults`: the maximum number of results to return.
- `startIndex`: the offset to start returning results, which is useful for pagination.
- `updatedMin`: this is a timestamp to indicate the last update time of the group. This is useful
  if you are periodically checking for changes in groups and you just want to know which ones
  have been modified/created after an specific timestamp.
  You can pass a timestamp in milliseconds, a Date object or use the format `yyyy-MM-dd'T'HH:mm:ss.SSSZ`.
- `showDeleted`: `true` if deleted groups should be listed, `false` otherwise. `false` by default.

Here is a sample:

```js
var res = app.endpoints.googleContacts.findGroups({
  maxResults: 3,
  startIndex: 3,
  showDeleted: true
});
log('count: '+res.groups.length);
```

### Create group

```js
var res = app.endpoints.googleContacts.createGroup(groupInfo);
```

Returns the created group. Here is a sample:

```js
var res = app.endpoints.googleContacts.createGroup({
    title: {
        type: 'text',
        text: 'Test Group A'
    }
});
log('group created: '+JSON.stringify(res));
```

### Update group

```js
var res = app.endpoints.googleContacts.updateGroup(groupInfo);
```

Returns the updated group. In the object the field `groupId` must be set and should be a valud one.
Here is a sample:

```js
sys.context.setCurrentUserByEmail('operator1@test.com');
var group = app.endpoints.googleContacts.findOneGroup('122e4f448dd31b2d');
group.title.text = 'Test Group A Modified';
var res = app.endpoints.googleContacts.updateGroup(group);
log('group updated: '+JSON.stringify(group));
```

### Delete group

```js
app.endpoints.googleContacts.deleteGroup(groupId);
```

Here is a sample:

```js
sys.context.setCurrentUserByEmail('operator1@test.com');
try {
    app.endpoints.googleContacts.deleteGroup('122e4f448dd31b2d');
    sys.logs.info('group deleted');
} catch(e) {
    sys.logs.error('group was not deleted: '+JSON.stringify(e));
}
```

## Events

It is important to keep in mind that as this is a user endpoint, events will be triggered for **each**
user connected to the endpoint (as long as they have permissions for it in Google). For example if
a contact is added in a group that is visible to more than one connected user, then multiple events
will arrive. You should call {% include js_symbol.html symbol='sys.context.getCurrentUserRecord()' %} 
to know which user the event is associated to.

### Contact updated

This event is only sent if the flag `Sync process` is enabled and will indicate that a contact
was created or updated. In the `data` field you will find the information about the contact:

```js
var info = event.data;
var contact = sys.data.findOne('contacts', {googleId: info.contactId});
if (!contact) {
  contact = sys.data.createRecord('contacts');
} 
contact.field('googleId').val(info.contactId);
contact.field('lastName').val(info.name.familyName);
contact.field('firstName').val(info.name.givenName);
var emails = [];
if(info.emailAddresses && info.emailAddresses.length > 0){
    for(var i in info.emailAddresses){
        emails.push(info.emailAddresses[i].address);
    }
}
contact.field('emails').val(emails);
sys.data.save(contact);
```

### Contact deleted

This event is only sent if the flag `Sync process` is enabled and will indicate that a contact
was deleted. In the `data` field you will find the information about the contact:

```js
var contact = sys.data.findOne('contacts', {googleId: event.data.contactId});
if (contact) {
  sys.logs.info('Contact ['+contact.label()+'] with id ['+contact.field('googleId').val()+'] was deleted');
  sys.data.remove(contact);
}
```

## About SLINGR

SLINGR is a low-code rapid application development platform that accelerates development, with robust architecture for integrations and executing custom workflows and automation.

[More info about SLINGR](https://slingr.io)

## License

This endpoint is licensed under the Apache License 2.0. See the `LICENSE` file for more details.


