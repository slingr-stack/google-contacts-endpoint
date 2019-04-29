package io.slingr.endpoints.googlecontacts.services.entities;

import com.google.gdata.data.contacts.Initials;
import com.google.gdata.data.contacts.Sensitivity;
import io.slingr.endpoints.utils.Json;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Created by lefunes on 19/06/15.
 */
public class BasePersonEntry extends AbstractField<com.google.gdata.data.contacts.BasePersonEntry> {
    public static final String LABEL = "basePersonEntry";
    public static final String LIST_LABEL = "basePersonEntryList";

    private BaseEntry baseEntry;
    private BillingInformation billingInformation;
    private Birthday birthday;
    private Gender gender;
    private String initials;
    private MaidenName maidenName;
    private Mileage mileage;
    private Nickname nickname;
    private Occupation occupation;
    private Priority priority;
    private String sensitivity;
    private ShortName shortName;
    private Subject subject;
    private DirectoryServer directoryServer;
    private List<UserDefinedField> userDefinedFields;
    private List<Website> websites;
    private Link contactPhotoLink;
    private Link contactEditPhotoLink;
    private List<ExtendedProperty> extendedProperties;
    private List<Im> imAddresses;
    private List<Email> emailAddresses;
    private Name name;
    private Status status;
    private Where where;
    private List<Organization> organizations;
    private List<PhoneNumber> phoneNumbers;
    private List<PostalAddress> postalAddresses;
    private List<StructuredPostalAddress> structuredPostalAddresses;
    private List<CalendarLink> calendarLinks;
    private List<Event> events;
    private List<ExternalId> externalIds;
    private List<Jot> jots;
    private List<Hobby> hobbies;
    private List<Relation> relations;

    public BasePersonEntry(BaseEntry baseEntry, BillingInformation billingInformation, Birthday birthday, Gender gender, String initials,
                           MaidenName maidenName, Mileage mileage, Nickname nickname, Occupation occupation, Priority priority,
                           String sensitivity, ShortName shortName, Subject subject, DirectoryServer directoryServer, List<UserDefinedField> userDefinedFields,
                           List<Website> websites, Link contactPhotoLink, Link contactEditPhotoLink,
                           List<ExtendedProperty> extendedProperties, List<Im> imAddresses, List<Email> emailAddresses,
                           Name name, Status status, Where where, List<Organization> organizations, List<PhoneNumber> phoneNumbers,
                           List<PostalAddress> postalAddresses, List<StructuredPostalAddress> structuredPostalAddresses,
                           List<CalendarLink> calendarLinks, List<Event> events, List<ExternalId> externalIds,
                           List<Jot> jots, List<Hobby> hobbies, List<Relation> relations) {
        this.baseEntry = baseEntry;
        this.billingInformation = billingInformation;
        this.birthday = birthday;
        this.gender = gender;
        this.initials = initials;
        this.maidenName = maidenName;
        this.mileage = mileage;
        this.nickname = nickname;
        this.occupation = occupation;
        this.priority = priority;
        this.sensitivity = sensitivity;
        this.shortName = shortName;
        this.subject = subject;
        this.directoryServer = directoryServer;
        this.userDefinedFields = userDefinedFields;
        this.websites = websites;
        this.contactPhotoLink = contactPhotoLink;
        this.contactEditPhotoLink = contactEditPhotoLink;
        this.extendedProperties = extendedProperties;
        this.imAddresses = imAddresses;
        this.emailAddresses = emailAddresses;
        this.name = name;
        this.status = status;
        this.where = where;
        this.organizations = organizations;
        this.phoneNumbers = phoneNumbers;
        this.postalAddresses = postalAddresses;
        this.structuredPostalAddresses = structuredPostalAddresses;
        this.calendarLinks = calendarLinks;
        this.events = events;
        this.externalIds = externalIds;
        this.jots = jots;
        this.hobbies = hobbies;
        this.relations = relations;
    }

    public static BasePersonEntry from(com.google.gdata.data.contacts.BasePersonEntry o) {
        if(o == null){
            return null;
        }
        return new BasePersonEntry(BaseEntry.from(o), BillingInformation.from(o.getBillingInformation()), Birthday.from(o.getBirthday()), Gender.from(o.getGender()),
                o.getInitials() != null ? o.getInitials().getValue() : null, MaidenName.from(o.getMaidenName()), Mileage.from(o.getMileage()), Nickname.from(o.getNickname()),
                Occupation.from(o.getOccupation()), Priority.from(o.getPriority()), o.getSensitivity() != null && o.getSensitivity().getRel() != null ? o.getSensitivity().getRel().name().toUpperCase() : null, ShortName.from(o.getShortName()),
                Subject.from(o.getSubject()), DirectoryServer.from(o.getDirectoryServer()), UserDefinedField.from(o.getUserDefinedFields()), Website.from(o.getWebsites()), Link.from(o.getContactPhotoLink()),
                Link.from(o.getContactEditPhotoLink()), ExtendedProperty.from(o.getExtendedProperties()), Im.from(o.getImAddresses()), Email.from(o.getEmailAddresses()),
                Name.from(o.getName()), Status.from(o.getStatus()), Where.from(o.getWhere()), Organization.from(o.getOrganizations()), PhoneNumber.from(o.getPhoneNumbers()),
                PostalAddress.from(o.getPostalAddresses()), StructuredPostalAddress.from(o.getStructuredPostalAddresses()),
                CalendarLink.from(o.getCalendarLinks()), Event.from(o.getEvents()), ExternalId.from(o.getExternalIds()),
                Jot.from(o.getJots()), Hobby.from(o.getHobbies()), Relation.from(o.getRelations()));
    }

    public static BasePersonEntry from(Json j) {
        if(j == null){
            return null;
        }
        return new BasePersonEntry(BaseEntry.from(j), BillingInformation.from(j.json("billingInformation")), Birthday.from(j.json("birthday")), Gender.from(j.json("gender")), j.string("initials"),
                MaidenName.from(j.json("maidenName")), Mileage.from(j.json("mileage")), Nickname.from(j.json("nickname")), Occupation.from(j.json("occupation")),
                Priority.from(j.json("priority")), j.string("sensitivity"), ShortName.from(j.json("shortName")), Subject.from(j.json("subject")), DirectoryServer.from(j.json("directoryServer")),
                UserDefinedField.from(j.objects("userDefinedFields")), Website.from(j.objects("websites")), Link.from(j.json("contactPhotoLink")), Link.from(j.json("contactEditPhotoLink")),
                ExtendedProperty.from(j.objects("extendedProperties")), Im.from(j.objects("imAddresses")), Email.from(j.objects("emailAddresses")), Name.from(j.json("name")), Status.from(j.json("status")),
                Where.from(j.json("where")), Organization.from(j.objects("organizations")), PhoneNumber.from(j.objects("phoneNumbers")), PostalAddress.from(j.objects("postalAddresses")),
                StructuredPostalAddress.from(j.objects("structuredPostalAddresses")), CalendarLink.from(j.objects("calendarLinks")), Event.from(j.objects("events")), ExternalId.from(j.objects("externalIds")),
                Jot.from(j.objects("jots")), Hobby.from(j.objects("hobbies")), Relation.from(j.objects("relations")));
    }

    public static List<BasePersonEntry> from(Collection l) {
        List<BasePersonEntry> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                BasePersonEntry basePersonEntry = null;
                try {
                    if (o instanceof Json || o instanceof Map) {
                        basePersonEntry = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.contacts.BasePersonEntry){
                        basePersonEntry = from((com.google.gdata.data.contacts.BasePersonEntry) o);
                    }
                } finally {
                    if(basePersonEntry != null){
                        list.add(basePersonEntry);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public void addToObject(com.google.gdata.data.contacts.BasePersonEntry be){
        if(be == null){
            return;
        }
        baseEntry.addToObject(be);
        be.setBillingInformation(billingInformation != null ? billingInformation.toObject() : null);
        be.setBirthday(birthday != null ? birthday.toObject() : null);
        be.setGender(gender != null ? gender.toObject() : null);

        be.setInitials(StringUtils.isNotBlank(initials) ? new Initials(initials) : null);
        be.setMaidenName(maidenName != null ? maidenName.toObject() : null);
        be.setMileage(mileage != null ? mileage.toObject() : null);
        be.setNickname(nickname != null ? nickname.toObject() : null);
        be.setOccupation(occupation != null ? occupation.toObject() : null);
        be.setPriority(priority != null ? priority.toObject() : null);
        be.setSensitivity(StringUtils.isNotBlank(sensitivity) ? new Sensitivity(Sensitivity.Rel.valueOf(sensitivity)) : null);
        be.setShortName(shortName != null ? shortName.toObject() : null);
        be.setSubject(subject != null ? subject.toObject() : null);
        be.setDirectoryServer(directoryServer != null ? directoryServer.toObject() : null);
        if(userDefinedFields != null) {
            for (UserDefinedField userDefinedField : userDefinedFields) {
                be.addUserDefinedField(userDefinedField.toObject());
            }
        }
        if(websites != null) {
            for (Website website : websites) {
                be.addWebsite(website.toObject());
            }
        }
        if(extendedProperties != null) {
            for (ExtendedProperty extendedProperty : extendedProperties) {
                be.addExtendedProperty(extendedProperty.toObject());
            }
        }
        if(imAddresses != null){
            for (Im imAddress : imAddresses) {
                be.addImAddress(imAddress.toObject());
            }
        }
        if(emailAddresses != null){
            for (Email emailAddress : emailAddresses) {
                be.addEmailAddress(emailAddress.toObject());
            }
        }
        be.setName(name != null ? name.toObject() : null);
        be.setStatus(status != null ? status.toObject() : null);
        be.setWhere(where != null ? where.toObject() : null);
        if(organizations != null){
            for (Organization organization : organizations) {
                be.addOrganization(organization.toObject());
            }
        }
        if(phoneNumbers != null){
            for (PhoneNumber phoneNumber : phoneNumbers) {
                be.addPhoneNumber(phoneNumber.toObject());
            }
        }
        if(postalAddresses != null){
            for (PostalAddress postalAddress : postalAddresses) {
                be.addPostalAddress(postalAddress.toObject());
            }
        }
        if(structuredPostalAddresses != null){
            for (StructuredPostalAddress structuredPostalAddress : structuredPostalAddresses) {
                be.addStructuredPostalAddress(structuredPostalAddress.toObject());
            }
        }
        if(calendarLinks != null){
            for (CalendarLink calendarLink : calendarLinks) {
                be.addCalendarLink(calendarLink.toObject());
            }
        }
        if(events != null){
            for (Event event : events) {
                be.addEvent(event.toObject());
            }
        }
        if(externalIds != null){
            for (ExternalId externalId : externalIds) {
                be.addExternalId(externalId.toObject());
            }
        }
        if(jots != null){
            for (Jot jot : jots) {
                be.addJot(jot.toObject());
            }
        }
        if(hobbies != null){
            for (Hobby hobby : hobbies) {
                be.addHobby(hobby.toObject());
            }
        }
        if(relations != null){
            for (Relation relation : relations) {
                be.addRelation(relation.toObject());
            }
        }
    }

    @Override
    public Json toJson(){
        Json j = baseEntry.toJson();

        if(billingInformation != null){
            billingInformation.addToJson(j);
        }
        if(birthday != null){
            birthday.addToJson(j);
        }
        if(gender != null){
            gender.addToJson(j);
        }
        if(StringUtils.isNotBlank(initials)){
            j.set("initials", initials);
        }
        if(maidenName != null){
            maidenName.addToJson(j);
        }
        if(mileage != null){
            mileage.addToJson(j);
        }
        if(nickname != null){
            nickname.addToJson(j);
        }
        if(occupation != null){
            occupation.addToJson(j);
        }
        if(priority != null){
            priority.addToJson(j);
        }
        if(StringUtils.isNotBlank(sensitivity)){
            j.set("sensitivity", sensitivity);
        }
        if(shortName != null){
            shortName.addToJson(j);
        }
        if(subject != null){
            subject.addToJson(j);
        }
        if(directoryServer != null){
            directoryServer.addToJson(j);
        }
        if(name != null){
            j.setIfNotEmpty("name", name.toJson().toMap());
        }
        if(where != null){
            j.setIfNotEmpty("where", where.toJson().toMap());
        }
        if(status != null){
            j.setIfNotEmpty("status", status.toJson().toMap());
        }
        if(contactPhotoLink != null){
            j.setIfNotEmpty("contactPhotoLink", contactPhotoLink.toJson().toMap());
        }
        if(contactEditPhotoLink != null){
            j.setIfNotEmpty("contactEditPhotoLink", contactEditPhotoLink.toJson().toMap());
        }
        if(userDefinedFields != null){
            final List<Map> jList = new ArrayList<>();
            userDefinedFields.stream()
                    .map(UserDefinedField::toJson)
                    .map(Json::toMap)
                    .forEach(jList::add);
            j.setIfNotEmpty("userDefinedFields", jList);
        }
        if(websites != null){
            final List<Map> jList = new ArrayList<>();
            websites.stream()
                    .map(Website::toJson)
                    .map(Json::toMap)
                    .forEach(jList::add);
            j.setIfNotEmpty("websites", jList);
        }
        if(extendedProperties != null){
            final List<Map> jList = new ArrayList<>();
            extendedProperties.stream()
                    .map(ExtendedProperty::toJson)
                    .map(Json::toMap)
                    .forEach(jList::add);
            j.setIfNotEmpty("extendedProperties", jList);
        }
        if(imAddresses != null){
            final List<Map> jList = new ArrayList<>();
            imAddresses.stream()
                    .map(Im::toJson)
                    .map(Json::toMap)
                    .forEach(jList::add);
            j.setIfNotEmpty("imAddresses", jList);
        }
        if(emailAddresses != null){
            final List<Map> jList = new ArrayList<>();
            emailAddresses.stream()
                    .map(Email::toJson)
                    .map(Json::toMap)
                    .forEach(jList::add);
            j.setIfNotEmpty("emailAddresses", jList);
        }
        if(organizations != null){
            final List<Map> jList = new ArrayList<>();
            organizations.stream()
                    .map(Organization::toJson)
                    .map(Json::toMap)
                    .forEach(jList::add);
            j.setIfNotEmpty("organizations", jList);
        }
        if(phoneNumbers != null){
            final List<Map> jList = new ArrayList<>();
            phoneNumbers.stream()
                    .map(PhoneNumber::toJson)
                    .map(Json::toMap)
                    .forEach(jList::add);
            j.setIfNotEmpty("phoneNumbers", jList);
        }
        if(postalAddresses != null){
            final List<Map> jList = new ArrayList<>();
            postalAddresses.stream()
                    .map(PostalAddress::toJson)
                    .map(Json::toMap)
                    .forEach(jList::add);
            j.setIfNotEmpty("postalAddresses", jList);
        }
        if(structuredPostalAddresses != null){
            final List<Map> jList = new ArrayList<>();
            structuredPostalAddresses.stream()
                    .map(StructuredPostalAddress::toJson)
                    .map(Json::toMap)
                    .forEach(jList::add);
            j.setIfNotEmpty("structuredPostalAddresses", jList);
        }
        if(calendarLinks != null){
            final List<Map> jList = new ArrayList<>();
            calendarLinks.stream()
                    .map(CalendarLink::toJson)
                    .map(Json::toMap)
                    .forEach(jList::add);
            j.setIfNotEmpty("calendarLinks", jList);
        }
        if(events != null){
            final List<Map> jList = new ArrayList<>();
            events.stream()
                    .map(Event::toJson)
                    .map(Json::toMap)
                    .forEach(jList::add);
            j.setIfNotEmpty("events", jList);
        }
        if(externalIds != null){
            final List<Map> jList = new ArrayList<>();
            externalIds.stream()
                    .map(ExternalId::toJson)
                    .map(Json::toMap)
                    .forEach(jList::add);
            j.setIfNotEmpty("externalIds", jList);
        }
        if(jots != null){
            final List<Map> jList = new ArrayList<>();
            jots.stream()
                    .map(Jot::toJson)
                    .map(Json::toMap)
                    .forEach(jList::add);
            j.setIfNotEmpty("jots", jList);
        }
        if(hobbies != null){
            final List<String> jList = new ArrayList<>();
            hobbies.stream()
                    .map(Hobby::toString)
                    .forEach(jList::add);
            j.setIfNotEmpty("hobbies", jList);
        }
        if(relations != null){
            final List<Map> jList = new ArrayList<>();
            relations.stream()
                    .map(Relation::toJson)
                    .map(Json::toMap)
                    .forEach(jList::add);
            j.setIfNotEmpty("relations", jList);
        }

        return j;
    }
}
