package io.slingr.endpoints.googlecontacts.services.entities;

import com.google.gdata.data.extensions.*;
import io.slingr.endpoints.utils.Json;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Created by lefunes on 23/06/15.
 */
public class Organization extends Field<com.google.gdata.data.extensions.Organization> {
    public static final String LABEL = "Organization";
    public static final String LIST_LABEL = "Organizations";

    private String label;
    private String department;
    private String name;
    private String job;
    private String symbol;
    private String title;
    private String rel;
    private boolean primary = false;

    public Organization(String label, String department, String name, String job, String symbol, String title, String rel, Boolean primary) {
        this.label = string(label);
        this.department = string(department);
        this.name = string(name);
        this.job = string(job);
        this.symbol = string(symbol);
        this.title = string(title);
        this.rel = string(rel);
        this.primary = bool(primary, false);
    }

    public static Organization from(com.google.gdata.data.extensions.Organization o) {
        if(o == null){
            return null;
        }
        String department = null;
        if(o.getOrgDepartment() != null){
            department = o.getOrgDepartment().getValue();
        }
        String name = null;
        if(o.getOrgName() != null){
            name = o.getOrgName().getValue();
        }
        String job = null;
        if(o.getOrgJobDescription() != null){
            job = o.getOrgJobDescription().getValue();
        }
        String symbol = null;
        if(o.getOrgSymbol() != null){
            symbol = o.getOrgSymbol().getValue();
        }
        String title = null;
        if(o.getOrgTitle() != null){
            title = o.getOrgTitle().getValue();
        }
        return new Organization(o.getLabel(), department, name, job, symbol, title, o.getRel(), o.getPrimary());
    }

    public static Organization from(Json j) {
        if(j == null){
            return null;
        }
        return new Organization(j.string("label"), j.string("department"), j.string("name"), j.string("job"), j.string("symbol"), j.string("title"), j.string("rel"), j.bool("primary"));
    }

    public static List<Organization> from(Collection l) {
        List<Organization> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                Organization organization = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        organization = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.extensions.Organization){
                        organization = from((com.google.gdata.data.extensions.Organization) o);
                    }
                } finally {
                    if(organization != null){
                        list.add(organization);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.extensions.Organization toObject(){
        final com.google.gdata.data.extensions.Organization org = new com.google.gdata.data.extensions.Organization(label, primary, rel);
        if(StringUtils.isNotBlank(department)){
            org.setOrgDepartment(new OrgDepartment(department));
        }
        if(StringUtils.isNotBlank(name)){
            org.setOrgName(new OrgName(name));
        }
        if(StringUtils.isNotBlank(job)){
            org.setOrgJobDescription(new OrgJobDescription(job));
        }
        if(StringUtils.isNotBlank(symbol)){
            org.setOrgSymbol(new OrgSymbol(symbol));
        }
        if(StringUtils.isNotBlank(title)){
            org.setOrgTitle(new OrgTitle(title));
        }
        return org;
    }

    @Override
    public Json toJson(){
        Json j = Json.map()
                .setIfNotEmpty("label", label)
                .setIfNotEmpty("department", department)
                .setIfNotEmpty("name", name)
                .setIfNotEmpty("job", job)
                .setIfNotEmpty("symbol", symbol)
                .setIfNotEmpty("title", title)
                .setIfNotEmpty("rel", rel);

        if(primary){
            j.set("primary", true);
        }
        return j;
    }
}
