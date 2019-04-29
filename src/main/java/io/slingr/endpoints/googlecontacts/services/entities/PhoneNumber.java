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
public class PhoneNumber extends Field<com.google.gdata.data.extensions.PhoneNumber> {
    public static final String LABEL = "phoneNumber";
    public static final String LIST_LABEL = "phoneNumbers";

    protected String rel;
    protected String label;
    protected String uri;
    protected String phoneNumber;
    protected boolean primary;

    public PhoneNumber(String rel, String label, String uri, String phoneNumber, Boolean primary) {
        this.rel = string(rel);
        this.label = string(label);
        this.uri = string(uri);
        this.phoneNumber = string(phoneNumber);
        this.primary = bool(primary, false);
    }

    public static PhoneNumber from(com.google.gdata.data.extensions.PhoneNumber o) {
        if(o == null){
            return null;
        }
        return new PhoneNumber(o.getRel(), o.getLabel(), o.getUri(), o.getPhoneNumber(), o.getPrimary());
    }

    public static PhoneNumber from(Json j) {
        if(j == null){
            return null;
        }
        return new PhoneNumber(j.string("rel"), j.string("label"), j.string("uri"), j.string("phoneNumber"), j.bool("primary"));
    }

    public static List<PhoneNumber> from(Collection l) {
        List<PhoneNumber> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                PhoneNumber phoneNumber = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        phoneNumber = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.extensions.PhoneNumber){
                        phoneNumber = from((com.google.gdata.data.extensions.PhoneNumber) o);
                    }
                } finally {
                    if(phoneNumber != null){
                        list.add(phoneNumber);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.extensions.PhoneNumber toObject(){
        final com.google.gdata.data.extensions.PhoneNumber o = new com.google.gdata.data.extensions.PhoneNumber();
        o.setRel(rel);
        o.setLabel(label);
        o.setUri(uri);
        o.setPhoneNumber(phoneNumber);
        o.setPrimary(primary);

        if(StringUtils.isBlank(o.getRel())) {
            if (StringUtils.isBlank(o.getLabel())) {
                o.setLabel("phone_number");
            }
        } else {
            switch (o.getRel().toLowerCase()){
                case "mobile":
                    o.setRel(com.google.gdata.data.extensions.PhoneNumber.Rel.MOBILE);
                    break;
                case "home":
                    o.setRel(com.google.gdata.data.extensions.PhoneNumber.Rel.HOME);
                    break;
                case "work":
                    o.setRel(com.google.gdata.data.extensions.PhoneNumber.Rel.WORK);
                    break;
                case "work_mobile":
                    o.setRel(com.google.gdata.data.extensions.PhoneNumber.Rel.WORK_MOBILE);
                    break;
                case "callback":
                    o.setRel(com.google.gdata.data.extensions.PhoneNumber.Rel.CALLBACK);
                    break;
                case "assistant":
                    o.setRel(com.google.gdata.data.extensions.PhoneNumber.Rel.ASSISTANT);
                    break;
                case "company_main":
                    o.setRel(com.google.gdata.data.extensions.PhoneNumber.Rel.COMPANY_MAIN);
                    break;
                case "internal-extension":
                    o.setRel(com.google.gdata.data.extensions.PhoneNumber.Rel.INTERNAL_EXTENSION);
                    break;
                case "fax":
                    o.setRel(com.google.gdata.data.extensions.PhoneNumber.Rel.FAX);
                    break;
                case "home_fax":
                    o.setRel(com.google.gdata.data.extensions.PhoneNumber.Rel.HOME_FAX);
                    break;
                case "work_fax":
                    o.setRel(com.google.gdata.data.extensions.PhoneNumber.Rel.WORK_FAX);
                    break;
                case "other_fax":
                    o.setRel(com.google.gdata.data.extensions.PhoneNumber.Rel.OTHER_FAX);
                    break;
                case "pager":
                    o.setRel(com.google.gdata.data.extensions.PhoneNumber.Rel.PAGER);
                    break;
                case "work_pager":
                    o.setRel(com.google.gdata.data.extensions.PhoneNumber.Rel.WORK_PAGER);
                    break;
                case "car":
                    o.setRel(com.google.gdata.data.extensions.PhoneNumber.Rel.CAR);
                    break;
                case "satellite":
                    o.setRel(com.google.gdata.data.extensions.PhoneNumber.Rel.SATELLITE);
                    break;
                case "radio":
                    o.setRel(com.google.gdata.data.extensions.PhoneNumber.Rel.RADIO);
                    break;
                case "tty_tdd":
                    o.setRel(com.google.gdata.data.extensions.PhoneNumber.Rel.TTY_TDD);
                    break;
                case "isdn":
                    o.setRel(com.google.gdata.data.extensions.PhoneNumber.Rel.ISDN);
                    break;
                case "telex":
                    o.setRel(com.google.gdata.data.extensions.PhoneNumber.Rel.TELEX);
                    break;
                case "other":
                    o.setRel(com.google.gdata.data.extensions.PhoneNumber.Rel.OTHER);
                    break;
                case "main":
                    o.setRel(com.google.gdata.data.extensions.PhoneNumber.Rel.MAIN);
                    break;
            }
        }




        return o;
    }

    @Override
    public Json toJson(){
        Json j = Json.map()
                .setIfNotEmpty("rel", rel)
                .setIfNotEmpty("label", label)
                .setIfNotEmpty("uri", uri)
                .setIfNotEmpty("phoneNumber", phoneNumber);

        if(primary){
            j.set("primary", true);
        }
        return j;
    }
}
