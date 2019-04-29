/////////////////////
// Public API
/////////////////////

endpoint.findOneContact = function (contactId, options) {
    options = checkOptions(contactId, options, 'contactId');
    checkValue(options, 'contactId');
    var response = endpoint._findOneContact(options);
    if (!response || isEmptyMap(response)) {
        return null;
    }
    return response;
};

endpoint.findContacts = function (params) {
    params = checkOptions(null, params);
    checkUpdatedMinParam(params);
    return endpoint._findContacts(params);
};

endpoint.createContact = function (contact) {
    contact = checkOptions(null, contact);
    return endpoint._createContact(contact);
};

endpoint.updateContact = function (contact) {
    contact = checkOptions(null, contact);
    checkValue(contact, 'contactId');
    return endpoint._updateContact(contact);
};

endpoint.deleteContact = function (contactId, options) {
    options = checkOptions(contactId, options, 'contactId');
    checkValue(options, 'contactId');
    var response = endpoint._deleteContact(options);
    if (!!response && !!response.__endpoint_exception__) {
        throw response;
    }
};

endpoint.findOneGroup = function (groupId, options) {
    options = checkOptions(groupId, options, 'groupId');
    checkValue(options, 'groupId');
    var response = endpoint._findOneGroup(options);
    if (!response || isEmptyMap(response)) {
        return null;
    }
    return response;
};

endpoint.findGroups = function (params) {
    params = checkOptions(null, params);
    checkUpdatedMinParam(params);
    return endpoint._findGroups(params);
};

endpoint.createGroup = function (group) {
    group = checkOptions(null, group);
    return endpoint._createGroup(group);
};

endpoint.updateGroup = function (group) {
    group = checkOptions(null, group);
    checkValue(group, 'groupId');
    return endpoint._updateGroup(group);
};

endpoint.deleteGroup = function (groupId, options) {
    options = checkOptions(groupId, options, 'groupId');
    checkValue(options, 'groupId');
    var response = endpoint._deleteGroup(options);
    if (!!response && !!response.__endpoint_exception__) {
        throw response;
    }
};

/////////////////////
// Utilities
/////////////////////

var checkUpdatedMinParam = function (params) {
    if (!!params && !!params.updatedMin) {
        var updatedMin = params.updatedMin;
        if (typeof updatedMin !== 'number') {
            if (typeof updatedMin === 'string') {
                updatedMin = Date.parse(updatedMin);
            } else if (typeof updatedMin === 'object' && updatedMin instanceof Date) {
                updatedMin = updatedMin.getTime();
            } else {
                throw new Error("Invalid updatedMin parameter. Only integers, string and Date objects are allowed.");
            }
            params.updatedMin = updatedMin;
        }
    }
};

var checkOptions = function (id, options, idKey) {
    options = options || {};
    if (!!id) {
        if (isObject(id)) {
            // take the 'id' parameter as the options
            options = id || {};
        } else if (!!idKey && !!id) {
            // replace the id on 'options' by the received on parameters
            options[idKey] = id;
        }
    }
    return options;
};

var checkValue = function (options, idKey) {
    if (!options[idKey]) {
        // exception if value is not present
        throw 'Empty ' + idKey;
    }
};

var checkHttpOptions = function (url, options) {
    options = options || {};
    if (!!url) {
        if (isObject(url)) {
            // take the 'url' parameter as the options
            options = url || {};
        } else {
            if (!!options.path || !!options.params || !!options.body) {
                // options contains the http package format
                options.path = url;
            } else {
                // create html package
                options = {
                    path: url,
                    body: options
                }
            }
        }
    }
    return options;
};

var stringType = Function.prototype.call.bind(Object.prototype.toString);

var isObject = function (obj) {
    return !!obj && stringType(obj) === '[object Object]'
};

var isEmptyMap = function(obj) {
    for(var prop in obj) {
        if(obj.hasOwnProperty(prop)) {
            return false;
        }
    }

    return JSON.stringify(obj) === JSON.stringify({});
};
