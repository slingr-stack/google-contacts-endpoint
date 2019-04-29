package io.slingr.endpoints.googlecontacts.services.entities;

import com.google.gdata.data.extensions.*;
import io.slingr.endpoints.utils.Json;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Created by lefunes on 19/06/15.
 */
public class StructuredPostalAddress extends Field<com.google.gdata.data.extensions.StructuredPostalAddress> {
    public static final String LABEL = "structuredPostalAddress";
    public static final String LIST_LABEL = "structuredPostalAddresses";

    private String label;
    private String mailClass;
    private String rel;
    private String usage;
    private String agent;
    private String city;
    private String countryCode;
    private String countryValue;
    private String formattedAddress;
    private String housename;
    private String neighborhood;
    private String pobox;
    private String postcode;
    private String region;
    private String street;
    private String subregion;
    private boolean primary = false;

    public StructuredPostalAddress(String label, String mailClass, String rel, String usage, String agent, String city,
                                   String countryCode, String countryValue, String formattedAddress, String housename,
                                   String neighborhood, String pobox, String postcode, String region, String street,
                                   String subregion, Boolean primary) {
        this.label = string(label);
        this.mailClass = string(mailClass);
        this.rel = string(rel);
        this.usage = string(usage);
        this.agent = string(agent);
        this.city = string(city);
        this.countryCode = string(countryCode);
        this.countryValue = string(countryValue);
        this.formattedAddress = string(formattedAddress);
        this.housename = string(housename);
        this.neighborhood = string(neighborhood);
        this.pobox = string(pobox);
        this.postcode = string(postcode);
        this.region = string(region);
        this.street = string(street);
        this.subregion = string(subregion);
        this.primary = bool(primary, false);
    }

    public static StructuredPostalAddress from(com.google.gdata.data.extensions.StructuredPostalAddress o) {
        if(o == null){
            return null;
        }
        String agent = null;
        if(o.hasAgent()){
            agent = o.getAgent().getValue();
        }
        String city = null;
        if(o.hasCity()){
            city = o.getCity().getValue();
        }
        String countryCode = null;
        String countryValue = null;
        if(o.hasCountry()){
            countryCode = o.getCountry().getCode();
            countryValue = o.getCountry().getValue();
        }
        String formattedAddress = null;
        if(o.hasFormattedAddress()){
            formattedAddress = o.getFormattedAddress().getValue();
        }
        String housename = null;
        if(o.hasHousename()){
            housename = o.getHousename().getValue();
        }
        String neighborhood = null;
        if(o.hasNeighborhood()){
            neighborhood = o.getNeighborhood().getValue();
        }
        String pobox = null;
        if(o.hasPobox()){
            pobox = o.getPobox().getValue();
        }
        String postcode = null;
        if(o.hasPostcode()){
            postcode = o.getPostcode().getValue();
        }
        String region = null;
        if(o.hasRegion()){
            region = o.getRegion().getValue();
        }
        String street = null;
        if(o.hasStreet()){
            street = o.getStreet().getValue();
        }
        String subregion = null;
        if(o.hasSubregion()){
            subregion = o.getSubregion().getValue();
        }
        return new StructuredPostalAddress(o.getLabel(), o.getMailClass(), o.getRel(), o.getUsage(), agent, city,
                countryCode, countryValue, formattedAddress, housename, neighborhood, pobox, postcode, region, street, subregion,
                o.getPrimary());
    }

    public static StructuredPostalAddress from(Json j) {
        if(j == null){
            return null;
        }
        return new StructuredPostalAddress(j.string("label"), j.string("mailClass"), j.string("rel"), j.string("usage"),
                j.string("agent"), j.string("city"), j.string("countryCode"), j.string("countryValue"), j.string("formattedAddress"),
                j.string("housename"), j.string("neighborhood"), j.string("pobox"), j.string("postcode"), j.string("region"),
                j.string("street"), j.string("subregion"), j.bool("primary"));
    }

    public static List<StructuredPostalAddress> from(Collection l) {
        List<StructuredPostalAddress> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                StructuredPostalAddress structuredPostalAddress = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        structuredPostalAddress = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.extensions.StructuredPostalAddress){
                        structuredPostalAddress = from((com.google.gdata.data.extensions.StructuredPostalAddress) o);
                    }
                } finally {
                    if(structuredPostalAddress != null){
                        list.add(structuredPostalAddress);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.extensions.StructuredPostalAddress toObject(){
        final com.google.gdata.data.extensions.StructuredPostalAddress o = new com.google.gdata.data.extensions.StructuredPostalAddress(label, mailClass, primary, rel, usage);
        if(StringUtils.isNotBlank(agent)) {
            o.setAgent(new Agent(agent));
        }
        if(StringUtils.isNotBlank(city)) {
            o.setCity(new City(city));
        }
        if(StringUtils.isNotBlank(countryCode) || StringUtils.isNotBlank(countryValue)) {
            o.setCountry(new Country(countryCode, countryValue));
        }
        if(StringUtils.isNotBlank(formattedAddress)) {
            o.setFormattedAddress(new FormattedAddress(formattedAddress));
        }
        if(StringUtils.isNotBlank(housename)) {
            o.setHousename(new HouseName(housename));
        }
        if(StringUtils.isNotBlank(neighborhood)) {
            o.setNeighborhood(new Neighborhood(neighborhood));
        }
        if(StringUtils.isNotBlank(pobox)) {
            o.setPobox(new PoBox(pobox));
        }
        if(StringUtils.isNotBlank(postcode)) {
            o.setPostcode(new PostCode(postcode));
        }
        if(StringUtils.isNotBlank(region)) {
            o.setRegion(new Region(region));
        }
        if(StringUtils.isNotBlank(street)) {
            o.setStreet(new Street(street));
        }
        if(StringUtils.isNotBlank(subregion)) {
            o.setSubregion(new Subregion(subregion));
        }

        return o;
    }

    @Override
    public Json toJson(){
        Json j = Json.map()
                .setIfNotEmpty("label", label)
                .setIfNotEmpty("mailClass", mailClass)
                .setIfNotEmpty("rel", rel)
                .setIfNotEmpty("usage", usage)
                .setIfNotEmpty("agent", agent)
                .setIfNotEmpty("city", city)
                .setIfNotEmpty("countryCode", countryCode)
                .setIfNotEmpty("countryValue", countryValue)
                .setIfNotEmpty("formattedAddress", formattedAddress)
                .setIfNotEmpty("housename", housename)
                .setIfNotEmpty("neighborhood", neighborhood)
                .setIfNotEmpty("pobox", pobox)
                .setIfNotEmpty("postcode", postcode)
                .setIfNotEmpty("region", region)
                .setIfNotEmpty("street", street)
                .setIfNotEmpty("subregion", subregion);

        if(primary){
            j.set("primary", true);
        }
        return j;
    }
}
